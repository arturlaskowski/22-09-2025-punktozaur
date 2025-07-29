package pl.punktozaur.coupon.domain.event;

import pl.punktozaur.coupon.domain.Coupon;

public class CouponActivatedEvent extends CouponEvent {

    public CouponActivatedEvent(Coupon coupon) {
        super(coupon);
    }
}