package pl.punktozaur.coupon.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.punktozaur.common.domain.event.DomainEvent;
import pl.punktozaur.coupon.domain.Coupon;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public abstract class CouponEvent implements DomainEvent {
    private final Coupon coupon;
    private final Instant createdAt;

    CouponEvent(Coupon coupon) {
        this.coupon = coupon;
        this.createdAt = Instant.now();
    }
}
