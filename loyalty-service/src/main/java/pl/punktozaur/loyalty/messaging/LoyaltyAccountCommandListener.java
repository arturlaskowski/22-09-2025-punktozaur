package pl.punktozaur.loyalty.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import pl.punktozaur.avro.loyalty.AddPointsAvroCommand;
import pl.punktozaur.avro.loyalty.LoyaltyAccountCommandAvroModel;
import pl.punktozaur.avro.loyalty.SubtractPointsAvroCommand;
import pl.punktozaur.common.domain.LoyaltyAccountId;
import pl.punktozaur.common.domain.LoyaltyPoints;
import pl.punktozaur.common.kafka.consumer.AbstractKafkaConsumer;
import pl.punktozaur.loyalty.application.LoyaltyAccountService;

import java.util.List;

import static org.springframework.kafka.support.KafkaHeaders.*;

@Slf4j
@Component
@RequiredArgsConstructor
class LoyaltyAccountCommandListener extends AbstractKafkaConsumer<LoyaltyAccountCommandAvroModel> {

    private final LoyaltyAccountService loyaltyAccountService;

    @Override
    @KafkaListener(id = "LoyaltyCommandListener",
            groupId = "${loyalty-service.kafka.group-id}",
            topics = "${loyalty-service.kafka.topics.loyalty-command}")
    public void receive(@Payload List<LoyaltyAccountCommandAvroModel> messages,
                        @Header(RECEIVED_KEY) List<String> keys,
                        @Header(RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(OFFSET) List<Long> offsets) {
        super.receive(messages, keys, partitions, offsets);
    }

    @Override
    protected void processMessage(LoyaltyAccountCommandAvroModel command) {
        switch (command.getType()) {
            case ADD_POINTS -> handle((AddPointsAvroCommand) command.getPayload());
            case SUBTRACT_POINTS -> handle((SubtractPointsAvroCommand) command.getPayload());
            default -> log.warn("Unknown payment command type: {}", command.getType());
        }
    }

    public void handle(SubtractPointsAvroCommand command) {
        var loyaltyPoints = new LoyaltyPoints(command.getPoints());
        var loyaltyAccountId = new LoyaltyAccountId(command.getLoyaltyAccountId());
        loyaltyAccountService.subtractPoints(loyaltyAccountId, loyaltyPoints);
    }

    public void handle(AddPointsAvroCommand command) {
        var loyaltyPoints = new LoyaltyPoints(command.getPoints());
        var loyaltyAccountId = new LoyaltyAccountId(command.getLoyaltyAccountId());
        loyaltyAccountService.addPoints(loyaltyAccountId, loyaltyPoints);
    }

    @Override
    protected String getMessageTypeName() {
        return "loyaltyCommand";
    }
}
