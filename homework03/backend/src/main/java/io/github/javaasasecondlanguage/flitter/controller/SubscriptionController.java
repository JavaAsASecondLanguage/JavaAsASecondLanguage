package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.dto.SubscriptionDto;
import io.github.javaasasecondlanguage.flitter.service.SubscriptionService;
import io.github.javaasasecondlanguage.flitter.service.UserService;
import io.github.javaasasecondlanguage.flitter.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static io.github.javaasasecondlanguage.flitter.util.Mapper.toUserNames;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService, UserService userService) {
        this.subscriptionService = subscriptionService;
        this.userService = userService;
    }

    @PostMapping("/subscribe")
    ResponseEntity<?> subscribe(@RequestBody SubscriptionDto subscriptionDto) {
        var authUser = userService.getUserByToken(subscriptionDto.getSubscriberToken());
        var subscription = Mapper.toSubscription(subscriptionDto);
        var createdSubscription = subscriptionService.create(authUser, subscription);
        return BaseResponse.ok(createdSubscription);
    }

    @PostMapping("/unsubscribe")
    ResponseEntity<?> unsubscribe(@RequestBody SubscriptionDto subscriptionDto) {
        var authUser = userService.getUserByToken(subscriptionDto.getSubscriberToken());
        var subscription = Mapper.toSubscription(subscriptionDto);
        subscriptionService.remove(authUser, subscription);
        return BaseResponse.ok();
    }

    @GetMapping("/subscribers/list/{usertoken}")
    ResponseEntity<?> subscribers(@PathVariable String usertoken) {
        var authUser = userService.getUserByToken(usertoken);
        var userNames = subscriptionService.findSubscriberNames(authUser);
        return BaseResponse.ok(userNames);
    }

    @GetMapping("/publishers/list/{userToken}")
    ResponseEntity<?> publishers(@PathVariable String userToken) {
        var authUser = userService.getUserByToken(userToken);
        var userNames = subscriptionService.findPublisherNames(authUser);
        return BaseResponse.ok(userNames);
    }
}
