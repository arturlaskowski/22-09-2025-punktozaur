package pl.punktozaur.coupon.saga;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.punktozaur.avro.loyalty.LoyaltyAccountCommandAvroModel;
import pl.punktozaur.avro.loyalty.LoyaltyAccountCommandType;
import pl.punktozaur.avro.loyalty.SubtractPointsAvroCommand;
import pl.punktozaur.common.kafka.producer.KafkaProducer;
import pl.punktozaur.coupon.domain.event.CouponActivatedEvent;
import pl.punktozaur.coupon.domain.event.CouponCreatedEvent;
import pl.punktozaur.coupon.domain.event.CouponRejectedEvent;
import pl.punktozaur.coupon.messaging.TopicsConfigData;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateCouponSagaDispatcher {

    private final CreateCouponSagaRepository createCouponSagaRepository;
    private final TopicsConfigData topics;
    private final KafkaProducer<String, LoyaltyAccountCommandAvroModel> loyaltyAccountCommandProducer;

    @Transactional
    public void handle(CouponCreatedEvent event) {
        CreateCouponSaga saga = CreateCouponSaga.create(event.getCoupon().getId().id(), event.getCoupon().getLoyaltyAccountId().id());
        createCouponSagaRepository.save(saga);

        sendSubtractPointsCommand(saga.getId(), event);
    }

    @Transactional
    public void handle(CouponActivatedEvent event) {
        var couponId = event.getCoupon().getId().id();
        CreateCouponSaga saga = createCouponSagaRepository.findByCouponId(couponId).orElseThrow();

        saga.complete();
        createCouponSagaRepository.save(saga);

        //coupon activated
    }

    @Transactional
    public void handle(CouponRejectedEvent event) {
        var couponId = event.getCoupon().getId().id();
        CreateCouponSaga saga = createCouponSagaRepository.findByCouponId(couponId).orElseThrow();

        saga.compensated(event.getCoupon().getFailureMessage());
        createCouponSagaRepository.save(saga);

        //coupon rejected
    }

    private void sendSubtractPointsCommand(UUID sagaId, CouponCreatedEvent event) {
        var loyaltyAccountId = event.getCoupon().getLoyaltyAccountId().id();
        var price = event.getCoupon().getNominalValue().getRequiredPoints().points();

        var subtractPointsAvroCommand = new SubtractPointsAvroCommand(sagaId,
                loyaltyAccountId,
                price,
                Instant.now());

        var loyaltyAccountCommandAvroModel = new LoyaltyAccountCommandAvroModel(
                LoyaltyAccountCommandType.SUBTRACT_POINTS,
                subtractPointsAvroCommand);

        loyaltyAccountCommandProducer.send(topics.getLoyaltyCommand(), loyaltyAccountId.toString(), loyaltyAccountCommandAvroModel);
    }

    public Optional<UUID> findCouponIdBySagaId(UUID sagaId) {
        return createCouponSagaRepository.findById(sagaId)
                .map(CreateCouponSaga::getCouponId);
    }
}