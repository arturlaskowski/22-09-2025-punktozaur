package pl.punktozaur.common.saga;

public enum SagaStatus {
    PROCESSING,
    SUCCEEDED,
    COMPENSATING,
    COMPENSATED,
    FAILED,
}