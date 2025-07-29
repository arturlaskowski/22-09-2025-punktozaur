package pl.punktozaur.coupon.application;


import pl.punktozaur.common.domain.event.DomainEventPublisher;
import pl.punktozaur.coupon.domain.event.CouponEvent;

public interface CouponEventPublisher extends DomainEventPublisher<CouponEvent> {

    void publish(CouponEvent event);
}
