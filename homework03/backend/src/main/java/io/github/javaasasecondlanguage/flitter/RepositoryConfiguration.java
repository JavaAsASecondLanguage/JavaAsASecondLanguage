package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.model.Flit;
import io.github.javaasasecondlanguage.flitter.model.Subscription;
import io.github.javaasasecondlanguage.flitter.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RepositoryConfiguration {

    @Bean("flitRepository")
    Map<String, Flit> flitRepository() {
        return Collections.synchronizedMap(new LinkedHashMap<>());
    }

    @Bean("userRepository")
    Map<String, User> userRepository() {
        return new ConcurrentHashMap<>();
    }

    @Bean("subscriptionRepository")
    Map<String, Subscription> subscriptionRepository() {
        return new ConcurrentHashMap<>();
    }
}
