package pl.punktozaur.coupon.application.repository;

import org.springframework.data.repository.CrudRepository;
import pl.punktozaur.coupon.domain.Coupon;
import pl.punktozaur.coupon.domain.CouponId;

public interface CouponRepository extends CrudRepository<Coupon, CouponId> {
}
