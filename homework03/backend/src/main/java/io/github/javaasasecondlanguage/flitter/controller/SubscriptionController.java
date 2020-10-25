package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.model.Subscription;
import io.github.javaasasecondlanguage.flitter.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionController {

    SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    ResponseEntity<?> subscribe(@RequestBody Subscription subscription) {
        var createdSubscription = subscriptionService.create(subscription);
        return BaseResponse.created(createdSubscription);
    }

    @PostMapping("/unsubscribe")
    ResponseEntity<?> unsubscribe(@RequestBody Subscription subscription) {
        subscriptionService.remove(subscription);
        return BaseResponse.empty();
    }

    @GetMapping("/subscribers/list/{usertoken}")
    ResponseEntity<?> subscribers(@RequestParam String userToken) {
        var users = subscriptionService.findSubscribers(userToken);
        return BaseResponse.ok(users);
    }

    @GetMapping("/publishers/list/{userToken}")
    ResponseEntity<?> publishers(@RequestParam String userToken) {
        var users = subscriptionService.findPublishers(userToken);
        return BaseResponse.ok(users);
    }
}
