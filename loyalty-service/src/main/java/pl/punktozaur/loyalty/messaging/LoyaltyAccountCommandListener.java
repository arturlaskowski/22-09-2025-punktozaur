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
import pl.punktozaur.common.kafka.consumer.AbstractKafkaConsumer;
import pl.punktozaur.loyalty.application.exception.LoyaltyAccountNotFoundException;

import java.util.List;

import static org.springframework.kafka.support.KafkaHeaders.*;

@Slf4j
@Component
@RequiredArgsConstructor
class LoyaltyAccountCommandListener extends AbstractKafkaConsumer<LoyaltyAccountCommandAvroModel> {

    private final LoyaltyMessageHandler loyaltyMessageHandler;

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
        try {
            switch (command.getType()) {
                case ADD_POINTS -> loyaltyMessageHandler.handle((AddPointsAvroCommand) command.getPayload());
                case SUBTRACT_POINTS -> loyaltyMessageHandler.handle((SubtractPointsAvroCommand) command.getPayload());
                default -> log.warn("Unknown payment command type: {}", command.getType());
            }
        }catch (LoyaltyAccountNotFoundException e){
            log.info("Do nothing");
        }

    }

    @Override
    protected String getMessageTypeName() {
        return "loyaltyCommand";
    }
}
