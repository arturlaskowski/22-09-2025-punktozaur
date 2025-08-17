package pl.punktozaur.customer.application.integration.loyalty;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.punktozaur.domain.CustomerId;
import pl.punktozaur.web.ServiceUnavailableException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoyaltyServiceClient {

    private final LoyaltyServiceFeignClient loyaltyServiceFeignClient;

    public void createLoyaltyAccount(CustomerId customerId) {
        try {
            log.info("Creating loyalty account for customer: {}", customerId.id());

            var request = new CreateLoyaltyAccountRequest(customerId.id());
            loyaltyServiceFeignClient.createLoyaltyAccount(request);

            log.info("Successfully created loyalty account for customer: {}", customerId.id());

        } catch (FeignException e) {
            log.error("Loyalty account creation failed with status: {}, message: {}", e.status(), e.getMessage());
            throw new ServiceUnavailableException("Loyalty", "creating loyalty account", e);

        } catch (Exception e) {
            log.error("Unexpected error while creating loyalty account for customer: {}", customerId.id(), e);
            throw new ServiceUnavailableException("Loyalty", "creating loyalty account", e);
        }
    }

}