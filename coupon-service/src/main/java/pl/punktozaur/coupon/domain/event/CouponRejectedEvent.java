package pl.punktozaur.coupon.domain.event;

import pl.punktozaur.coupon.domain.Coupon;

public class CouponRejectedEvent extends CouponEvent {

    public CouponRejectedEvent(Coupon coupon) {
        super(coupon);
    }
}