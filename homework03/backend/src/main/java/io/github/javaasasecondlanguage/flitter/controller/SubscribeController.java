package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.base.Response;
import io.github.javaasasecondlanguage.flitter.base.Subscribe;
import io.github.javaasasecondlanguage.flitter.model.SubscribeModel;
import io.github.javaasasecondlanguage.flitter.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscribeController {

    @PostMapping("/subscribe")
    ResponseEntity<?> subscribe(@RequestBody Subscribe subscribe) {
        if (UserModel.containsToken(subscribe.getSubscriberToken())
                && UserModel.containsUser(subscribe.getPublisherName())) {
            SubscribeModel.subscribe(UserModel.getUserByToken(subscribe.getSubscriberToken()),
                    UserModel.getUserByName(subscribe.getPublisherName()));
            return ResponseEntity.ok(new Response<>("Success"));
        } else {
            return ResponseEntity.badRequest().body(new Response<>(null, "User not found"));
        }
    }

    @PostMapping("/unsubscribe")
    ResponseEntity<?> unSubscribe(@RequestBody Subscribe subscribe) {
        if (UserModel.containsToken(subscribe.getSubscriberToken()) &&
                UserModel.containsUser(subscribe.getPublisherName())) {
            SubscribeModel.unSubscribe(UserModel.getUserByToken(subscribe.getSubscriberToken()),
                    UserModel.getUserByName(subscribe.getPublisherName()));
            return ResponseEntity.ok(new Response<>("Success"));
        } else {
            return ResponseEntity.badRequest().body(new Response<>(null, "User not found"));
        }
    }

    @GetMapping("/subscribers/list/{userToken}")
    public ResponseEntity<?> getSubscribers(@PathVariable String userToken) {
        if (UserModel.containsToken(userToken)) {
            return ResponseEntity.ok(
                    new Response<>(SubscribeModel.getSubscribers(UserModel.getUserByToken(userToken))));
        } else {
            return ResponseEntity.badRequest().body(new Response<>(null, "User not found"));
        }
    }

    @GetMapping("/publishers/list/{userToken}")
    public ResponseEntity<?> getPublishers(@PathVariable String userToken) {
        if (UserModel.containsToken(userToken)) {
            return ResponseEntity.ok(
                    new Response<>(SubscribeModel.getPublishers(UserModel.getUserByToken(userToken))));
        } else {
            return ResponseEntity.badRequest().body(new Response<>(null, "User not found"));
        }
    }

}
