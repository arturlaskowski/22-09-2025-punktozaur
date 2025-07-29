package pl.punktozaur.coupon.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.punktozaur.avro.loyalty.LoyaltyAccountCommandAvroModel;
import pl.punktozaur.avro.loyalty.LoyaltyAccountCommandType;
import pl.punktozaur.avro.loyalty.SubtractPointsAvroCommand;
import pl.punktozaur.common.domain.LoyaltyAccountId;
import pl.punktozaur.common.domain.LoyaltyPoints;
import pl.punktozaur.common.kafka.producer.KafkaProducer;
import pl.punktozaur.coupon.application.LoyaltyAccountCommandPublisher;

import java.time.Instant;

@Component
@RequiredArgsConstructor
//Sage można zaimplmentować na różne sposoby, ta klasa może być całkowicie usunięta :)
public class KafkaLoyaltyAccountCommandPublisher implements LoyaltyAccountCommandPublisher {

    private final TopicsConfigData topicsConfigData;
    private final KafkaProducer<String, LoyaltyAccountCommandAvroModel> kafkaProducer;

    @Override
    public void publishSubtractPointsCommand(LoyaltyAccountId loyaltyAccountId, LoyaltyPoints loyaltyPoints) {
        var subtractPointsAvroCommand = new SubtractPointsAvroCommand(
                null, // TODO
                loyaltyAccountId.id(),
                loyaltyPoints.points(),
                Instant.now()
        );

        var loyaltyAccountCommandAvroModel = new LoyaltyAccountCommandAvroModel(
                LoyaltyAccountCommandType.SUBTRACT_POINTS,
                subtractPointsAvroCommand
        );

        kafkaProducer.send(topicsConfigData.getLoyaltyCommand(),
                loyaltyAccountId.id().toString(),
                loyaltyAccountCommandAvroModel);
    }
}
