
package pl.punktozaur.coupon.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import pl.punktozaur.avro.loyalty.LoyaltyAccountEventAvroModel;
import pl.punktozaur.avro.loyalty.PointsAddedAvroEvent;
import pl.punktozaur.avro.loyalty.PointsSubtractedAvroEvent;
import pl.punktozaur.avro.loyalty.PointsSubtractionFailedAvroEvent;
import pl.punktozaur.common.kafka.consumer.AbstractKafkaConsumer;
import pl.punktozaur.coupon.application.CouponService;
import pl.punktozaur.coupon.domain.CouponId;
import pl.punktozaur.coupon.saga.CreateCouponSagaDispatcher;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
class LoyaltyAccountEventListener extends AbstractKafkaConsumer<LoyaltyAccountEventAvroModel> {

    private final CouponService couponService;
    private final CreateCouponSagaDispatcher sagaDispatcher;

    @Override
    @KafkaListener(
            id = "LoyaltyPointsProcessedEventListener",
            groupId = "${coupon-service.kafka.group-id}",
            topics = "${coupon-service.kafka.topics.loyalty-event}"
    )
    public void receive(@Payload List<LoyaltyAccountEventAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        super.receive(messages, keys, partitions, offsets);
    }

    @Override
    protected void processMessage(LoyaltyAccountEventAvroModel event) {
        switch (event.getType()) {
            case POINTS_ADDED -> handlePointsAdded((PointsAddedAvroEvent) event.getPayload());
            case POINTS_SUBTRACTED -> handlePointsSubtracted((PointsSubtractedAvroEvent) event.getPayload());
            case POINTS_SUBTRACTION_FAILED ->
                    handlePointsSubtractionFailed((PointsSubtractionFailedAvroEvent) event.getPayload());
        }
    }

    private void handlePointsAdded(PointsAddedAvroEvent event) {
        // Jeśli byłby proces biznesowy, że po przyjściu eventu PointsSubtractedEvent nie można zmienić kuponu na aktywny,
        // bo np. może istnieć tylko jeden kupon aktywny tego typu dla danego konta, to wtedy też byśmy wysyłali command do dodania puntków i słuchali na ten event.
    }

    private void handlePointsSubtracted(PointsSubtractedAvroEvent event) {
        sagaDispatcher.findCouponIdBySagaId(event.getSagaId())
                .ifPresentOrElse(
                        couponId -> couponService.activeCoupon(new CouponId(couponId)),
                        () -> log.warn("No coupon associated with sagaId {} for PointsSubtracted event", event.getSagaId())
                );
    }


    private void handlePointsSubtractionFailed(PointsSubtractionFailedAvroEvent event) {
        sagaDispatcher.findCouponIdBySagaId(event.getSagaId())
                .ifPresentOrElse(
                        couponId -> couponService.rejectCoupon(new CouponId(couponId), event.getFailureMessages()),
                        () -> log.warn("No coupon associated with sagaId {} for PointsSubtractionFailed event", event.getSagaId())
                );
    }

    @Override
    protected String getMessageTypeName() {
        return "loyaltyEvent";
    }
}