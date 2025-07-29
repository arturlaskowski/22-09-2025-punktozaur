package pl.punktozaur.loyalty.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.punktozaur.avro.loyalty.AddPointsAvroCommand;
import pl.punktozaur.avro.loyalty.SubtractPointsAvroCommand;
import pl.punktozaur.common.domain.LoyaltyAccountId;
import pl.punktozaur.common.domain.LoyaltyPoints;
import pl.punktozaur.loyalty.application.LoyaltyAccountService;
import pl.punktozaur.loyalty.application.exception.InsufficientPointsException;
import pl.punktozaur.loyalty.application.exception.LoyaltyAccountNotFoundException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoyaltyMessageHandler {

    private final LoyaltyAccountService service;
    private final LoyaltyEventPublisher loyaltyEventPublisher;

    public void handle(SubtractPointsAvroCommand command) {
        var loyaltyAccountId = new LoyaltyAccountId(command.getLoyaltyAccountId());
        var loyaltyPoints = new LoyaltyPoints(command.getPoints());
        try {
            service.subtractPoints(loyaltyAccountId, loyaltyPoints);
            loyaltyEventPublisher.publishPointsSubtractedEvent(command.getSagaId(), loyaltyAccountId.id(), loyaltyPoints.points());
        } catch (LoyaltyAccountNotFoundException | InsufficientPointsException e) {
            String errorMessage = String.format("Failed to subtract points: %s", e.getMessage());
            loyaltyEventPublisher.publishPointsSubtractionFailed(command.getSagaId(), loyaltyAccountId.id(), errorMessage);
            log.warn("Failed to subtract points for loyalty account [{}]: {}", loyaltyAccountId.id(), errorMessage);
        } catch (Exception e) {
            log.error("Unexpected error while processing subtract points command for loyalty account: {} with {} points. Error: {}",
                    loyaltyAccountId.id(), loyaltyPoints.points(), e.getMessage(), e);
            throw e;
        }
    }

    public void handle(AddPointsAvroCommand command) {
        var loyaltyAccountId = new LoyaltyAccountId(command.getLoyaltyAccountId());
        var loyaltyPoints = new LoyaltyPoints(command.getPoints());
        service.addPoints(loyaltyAccountId, loyaltyPoints);
        loyaltyEventPublisher.publishPointsAddedAvroEvent(command.getSagaId(), loyaltyAccountId.id(), loyaltyPoints.points());
    }
}