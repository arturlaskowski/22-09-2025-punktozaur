package pl.punktozaur.coupon.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.punktozaur.coupon.application.CouponEventPublisher;
import pl.punktozaur.coupon.domain.event.CouponActivatedEvent;
import pl.punktozaur.coupon.domain.event.CouponCreatedEvent;
import pl.punktozaur.coupon.domain.event.CouponEvent;
import pl.punktozaur.coupon.domain.event.CouponRejectedEvent;
import pl.punktozaur.coupon.saga.CreateCouponSagaDispatcher;

@Component
@RequiredArgsConstructor
@Slf4j
class KafkaCouponEventPublisher implements CouponEventPublisher {

    private final CreateCouponSagaDispatcher createCouponSagaDispatcher;

    // Gdyby orchestrator sagi znajdował się w innym module, te eventy byłyby normalnie publikowane na Kafkę.
    // Jednak ponieważ znajduje się w tym samym module, można bezpośrednio wywołać komponent sagi.
    @Override
    public void publish(CouponEvent event) {
        switch (event) {
            case CouponCreatedEvent createdEvent -> createCouponSagaDispatcher.handle(createdEvent);
            case CouponActivatedEvent activatedEvent -> createCouponSagaDispatcher.handle(activatedEvent);
            case CouponRejectedEvent rejectedEvent -> createCouponSagaDispatcher.handle(rejectedEvent);
            default ->
                    throw new IllegalArgumentException("Unsupported event type: " + event.getClass().getSimpleName());
        }
    }
}