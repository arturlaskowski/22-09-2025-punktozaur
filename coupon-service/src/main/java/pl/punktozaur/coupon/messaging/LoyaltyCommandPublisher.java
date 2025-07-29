package pl.punktozaur.coupon.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.punktozaur.avro.loyalty.SubtractPointsCommandAvroModel;
import pl.punktozaur.common.domain.LoyaltyAccountId;
import pl.punktozaur.common.domain.LoyaltyPoints;
import pl.punktozaur.common.kafka.producer.KafkaProducer;
import pl.punktozaur.coupon.application.SubtractPointsCommandPublisher;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LoyaltyCommandPublisher implements SubtractPointsCommandPublisher {

    private final TopicsConfigData topicsConfigData;
    private final KafkaProducer<String, SubtractPointsCommandAvroModel> kafkaProducer;

    @Override
    public void publishSubtractPointsCommand(LoyaltyAccountId loyaltyAccountId, LoyaltyPoints loyaltyPoints) {
        var processPaymentCommandAvroModel = new SubtractPointsCommandAvroModel(
                loyaltyAccountId.id(),
                loyaltyPoints.points(),
                Instant.now()
        );

        kafkaProducer.send(topicsConfigData.getLoyaltyCommand(),
                loyaltyAccountId.id().toString(),
                processPaymentCommandAvroModel);
    }
}
