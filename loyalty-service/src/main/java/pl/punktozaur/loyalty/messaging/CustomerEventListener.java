package pl.punktozaur.loyalty.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import pl.punktozaur.avro.customer.CustomerEventAvroModel;
import pl.punktozaur.avro.customer.CustomerEventType;
import pl.punktozaur.common.kafka.consumer.AbstractKafkaConsumer;
import pl.punktozaur.loyalty.application.LoyaltyAccountService;
import pl.punktozaur.loyalty.application.dto.CreateLoyaltyAccountDto;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class CustomerEventListener extends AbstractKafkaConsumer<CustomerEventAvroModel> {

    private final LoyaltyAccountService loyaltyAccountService;

    @Override
    @KafkaListener(id = "CreatedCustomerEventListener",
            groupId = "${loyalty-service.kafka.group-id}",
            topics = "${loyalty-service.kafka.topics.customer-event}")
    public void receive(@Payload List<CustomerEventAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        super.receive(messages, keys, partitions, offsets);
    }

    @Override
    protected void processMessage(CustomerEventAvroModel event) {
        if (event.getType().equals(CustomerEventType.CUSTOMER_CREATED)) {
            loyaltyAccountService.addAccount(new CreateLoyaltyAccountDto(event.getCustomerId()));
        }
    }

    @Override
    protected String getMessageTypeName() {
        return "customerEvent";
    }
}