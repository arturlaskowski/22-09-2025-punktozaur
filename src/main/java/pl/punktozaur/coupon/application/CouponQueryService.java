package pl.punktozaur.coupon.application;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.punktozaur.coupon.application.dto.CouponDto;
import pl.punktozaur.coupon.application.exception.CouponNotFoundException;
import pl.punktozaur.coupon.application.repository.CouponQueryRepository;
import pl.punktozaur.coupon.domain.CouponId;

@Service
@AllArgsConstructor
@Slf4j
public class CouponQueryService {

    private final CouponQueryRepository couponQueryRepository;

    @Transactional(readOnly = true)
    public CouponDto getCoupon(CouponId id) {
        var coupon = couponQueryRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));

        return new CouponDto(coupon.getId().id(), coupon.getLoyaltyAccountId().id(),
                coupon.getNominalValue(), coupon.isActive());
    }
}
