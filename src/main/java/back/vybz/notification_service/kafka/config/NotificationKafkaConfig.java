package back.vybz.notification_service.kafka.config;

import back.vybz.notification_service.kafka.event.ChatNotificationEvent;
import back.vybz.notification_service.kafka.event.FollowNotificationEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class NotificationKafkaConfig {

    private final CommonKafkaConfig commonKafkaConfig;

    @Bean
    public ConsumerFactory<String, FollowNotificationEvent> followNotificationEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(FollowNotificationEvent.class, false))
        );
    }

    @Bean(name = "followNotificationKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, FollowNotificationEvent> followNotificationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FollowNotificationEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(followNotificationEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ChatNotificationEvent> chatNotificationEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonKafkaConfig.commonConsumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(ChatNotificationEvent.class, false))
        );
    }

    @Bean(name = "chatNotificationKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ChatNotificationEvent> chatNotificationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatNotificationEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(chatNotificationEventConsumerFactory());
        return factory;
    }

}
