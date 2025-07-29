package pl.punktozaur.coupon.domain.event;


import pl.punktozaur.coupon.domain.Coupon;

public class CouponCreatedEvent extends CouponEvent {

    public CouponCreatedEvent(Coupon coupon) {
        super(coupon);
    }
}
