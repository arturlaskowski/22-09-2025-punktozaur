package pl.punktozaur.loyalty.messaging;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "loyalty-service.kafka.topics")
public class TopicsConfigData {
    private String customerEvent;
    private String loyaltyCommand;
    private String loyaltyEvent;
}