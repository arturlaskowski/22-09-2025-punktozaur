package pl.punktozaur.coupon.application;


import pl.punktozaur.common.domain.LoyaltyAccountId;
import pl.punktozaur.common.domain.LoyaltyPoints;

public interface SubtractPointsCommandPublisher {

    void publishSubtractPointsCommand(LoyaltyAccountId loyaltyAccountId, LoyaltyPoints points);
}
