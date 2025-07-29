package pl.punktozaur.coupon.saga;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.punktozaur.common.saga.SagaStatus;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "coupon_sagas")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class CreateCouponSaga {

    @Id
    private UUID id;

    @Column(unique = true)
    private UUID couponId;

    private UUID loyaltyAccountId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SagaStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(length = 2000)
    private String errorMessage;

    @Version
    private int version;

    public static CreateCouponSaga create(UUID couponId, UUID loyaltyAccountId) {
        CreateCouponSaga saga = new CreateCouponSaga();
        saga.id = UUID.randomUUID();
        saga.couponId = couponId;
        saga.loyaltyAccountId = loyaltyAccountId;
        saga.status = SagaStatus.PROCESSING;
        saga.createdAt = Instant.now();
        saga.updatedAt = Instant.now();
        return saga;
    }

    public void compensated(String errorMessage) {
        this.status = SagaStatus.COMPENSATED;
        this.updatedAt = Instant.now();
        this.errorMessage = errorMessage;
    }

    public void complete() {
        this.status = SagaStatus.SUCCEEDED;
        this.updatedAt = Instant.now();
    }
}