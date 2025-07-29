package pl.punktozaur.loyalty.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pl.punktozaur.avro.loyalty.*;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoyaltyEventPublisher {

    private final KafkaTemplate<String, LoyaltyAccountEventAvroModel> kafkaTemplate;
    private final TopicsConfigData topicsConfigData;

    void publishPointsSubtractedEvent(UUID sagaId, UUID loyaltyAccountId, int points) {
        var subtractedEvent = new PointsSubtractedAvroEvent(sagaId, loyaltyAccountId, points, Instant.now());
        var event = new LoyaltyAccountEventAvroModel(LoyaltyAccountEventType.POINTS_SUBTRACTED, subtractedEvent);
        kafkaTemplate.send(topicsConfigData.getLoyaltyEvent(), loyaltyAccountId.toString(), event);
        log.info("Points subtracted event sent to kafka for saga id: {} and loyalty account id: {}", sagaId, loyaltyAccountId);
    }

    void publishPointsSubtractionFailed(UUID sagaId, UUID loyaltyAccountId, String reason) {
        var subtractionFailedEvent = new PointsSubtractionFailedAvroEvent(sagaId, loyaltyAccountId, reason, Instant.now());
        var event = new LoyaltyAccountEventAvroModel(LoyaltyAccountEventType.POINTS_SUBTRACTION_FAILED, subtractionFailedEvent);
        kafkaTemplate.send(topicsConfigData.getLoyaltyEvent(), loyaltyAccountId.toString(), event);
        log.info("Points subtraction failed event sent to kafka for saga id: {} and loyalty account id: {}, reason: {}", sagaId, loyaltyAccountId, reason);
    }

    void publishPointsAddedAvroEvent(UUID sagaId, UUID loyaltyAccountId, int points) {
        var addedEvent = new PointsAddedAvroEvent(sagaId, loyaltyAccountId, points, Instant.now());
        var event = new LoyaltyAccountEventAvroModel(LoyaltyAccountEventType.POINTS_ADDED, addedEvent);
        kafkaTemplate.send(topicsConfigData.getLoyaltyEvent(), loyaltyAccountId.toString(), event);
        log.info("Points added event sent to kafka for saga id: {} and loyalty account id: {}", sagaId, loyaltyAccountId);
    }
}
