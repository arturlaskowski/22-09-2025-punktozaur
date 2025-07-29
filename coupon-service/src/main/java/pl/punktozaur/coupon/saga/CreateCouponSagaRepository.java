package pl.punktozaur.coupon.saga;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

interface CreateCouponSagaRepository extends CrudRepository<CreateCouponSaga, UUID> {

    Optional<CreateCouponSaga> findByCouponId(UUID id);
}
