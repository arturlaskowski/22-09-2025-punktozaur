package pl.punktozaur.customer.domain;


import pl.punktozaur.common.domain.event.DomainEventPublisher;
import pl.punktozaur.customer.domain.event.CustomerEvent;

public interface CustomerEventPublisher extends DomainEventPublisher<CustomerEvent> {
}
