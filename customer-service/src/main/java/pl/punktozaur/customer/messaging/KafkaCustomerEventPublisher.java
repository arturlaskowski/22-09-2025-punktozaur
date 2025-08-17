package pl.punktozaur.customer.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.punktozaur.avro.customer.CustomerEventAvroModel;
import pl.punktozaur.avro.customer.CustomerEventType;
import pl.punktozaur.common.kafka.producer.KafkaProducer;
import pl.punktozaur.customer.domain.CustomerEventPublisher;
import pl.punktozaur.customer.domain.event.CustomerEvent;

@Component
@RequiredArgsConstructor
class KafkaCustomerEventPublisher implements CustomerEventPublisher {

    private final TopicsConfigData topicsConfigData;
    private final KafkaProducer<String, CustomerEventAvroModel> kafkaProducer;

    @Override
    public void publish(CustomerEvent customerEvent) {
        var customerId = customerEvent.getCustomer().getCustomerId().id();
        var customerEventAvroModel = new CustomerEventAvroModel(customerId,
                CustomerEventType.CUSTOMER_CREATED, customerEvent.getCreatedAt());

        kafkaProducer.send(topicsConfigData.getCustomerEvent(),
                customerId.toString(),
                customerEventAvroModel);
    }
}
