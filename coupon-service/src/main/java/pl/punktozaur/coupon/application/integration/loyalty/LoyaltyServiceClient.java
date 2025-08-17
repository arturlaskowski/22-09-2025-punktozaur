package pl.punktozaur.coupon.application.integration.loyalty;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.punktozaur.coupon.application.exception.PointsNotSubtractedException;
import pl.punktozaur.domain.LoyaltyAccountId;
import pl.punktozaur.domain.LoyaltyPoints;
import pl.punktozaur.web.ServiceUnavailableException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoyaltyServiceClient {

    private final LoyaltyServiceFeignClient loyaltyServiceFeignClient;

    public void subtractPoints(LoyaltyAccountId accountId, LoyaltyPoints pointsToSubtract) {
        try {
            log.info("Attempting to subtract {} points from loyalty account: {}",
                    pointsToSubtract.points(), accountId.id());

            SubtractPointsRequest request = new SubtractPointsRequest(pointsToSubtract.points());
            loyaltyServiceFeignClient.subtractPoints(accountId.id(), request);

            log.info("Successfully subtracted {} points from loyalty account: {}",
                    pointsToSubtract.points(), accountId.id());

        } catch (FeignException e) {
            log.error("Points subtraction failed with status: {}, message: {}", e.status(), e.getMessage());

            // If status is 5xx - loyalty service issue
            if (HttpStatus.Series.valueOf(e.status()) == HttpStatus.Series.SERVER_ERROR) {
                throw new ServiceUnavailableException("Loyalty", "subtracting points", e);
            }

            // For all other non-200 status codes - points subtraction issue
            throw new PointsNotSubtractedException(accountId);

        } catch (Exception e) {
            if (e instanceof PointsNotSubtractedException || e instanceof ServiceUnavailableException) {
                throw e;
            }

            log.error("Unexpected error while subtracting points: {}", e.getMessage(), e);
            throw new ServiceUnavailableException("Loyalty", "subtracting points", e);
        }
    }
}