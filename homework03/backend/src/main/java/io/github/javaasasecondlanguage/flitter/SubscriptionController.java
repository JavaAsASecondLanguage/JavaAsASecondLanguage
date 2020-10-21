package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.pojos.Response;
import io.github.javaasasecondlanguage.flitter.pojos.SubscriptionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SubscriptionController {

    @PostMapping("subscribe")
    public ResponseEntity<Response<String>> subscribe(@RequestBody SubscriptionRequest subscriptionRequest) {
        UUID subscriberUUID;
        try {
            subscriberUUID = UUID.fromString(subscriptionRequest.getSubscriberToken());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new Response<>(null, "Bad subscriber token"),
                    HttpStatus.BAD_REQUEST);
        }

        Map<UUID, String> userTokens = Context.getInstance().getUserTokens();
        if (!userTokens.containsKey(subscriberUUID)) {
            return new ResponseEntity<>(
                    new Response<>("Subscriber is not registered"),
                    HttpStatus.NOT_FOUND);
        }
        Optional<UUID> publisherId = userTokens.entrySet().stream()
                .filter(entry -> entry.getValue().equals(subscriptionRequest.getPublisherName()))
                .map(Map.Entry::getKey)
                .findFirst();
        if (publisherId.isEmpty()) {
            return new ResponseEntity<>(
                    new Response<>("Publisher is not registered"),
                    HttpStatus.NOT_FOUND);
        }

        Context.getInstance().getSubscriptions()
                .putIfAbsent(subscriberUUID, new ArrayList<>());
        Context.getInstance().getSubscriptions()
                .get(subscriberUUID).add(publisherId.get());
        return new ResponseEntity<>(
                new Response<>("Success", null),
                HttpStatus.OK);
    }

    @PostMapping("unsubscribe")
    ResponseEntity<Response<String>> unsubscribe(@RequestBody SubscriptionRequest request) {
        UUID subscriberUUID;
        try {
            subscriberUUID = UUID.fromString(request.getSubscriberToken());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new Response<>(null, "Bad subscriber token"),
                    HttpStatus.BAD_REQUEST);
        }

        Map<UUID, String> userTokens = Context.getInstance().getUserTokens();
        if (!userTokens.containsKey(subscriberUUID)) {
            return new ResponseEntity<>(
                    new Response<>("Subscriber is not registered"),
                    HttpStatus.NOT_FOUND);
        }
        Optional<UUID> publisherId = userTokens.entrySet().stream()
                .filter(entry -> entry.getValue().equals(request.getPublisherName()))
                .map(Map.Entry::getKey)
                .findFirst();
        if (publisherId.isEmpty()) {
            return new ResponseEntity<>(
                    new Response<>("Publisher is not registered"),
                    HttpStatus.NOT_FOUND);
        }

        List<UUID> publisherUuids = Context.getInstance().getSubscriptions()
                .get(subscriberUUID);
        if (publisherUuids != null && publisherUuids.contains(publisherId.get())) {
            publisherUuids.remove(publisherId.get());
        } else {
            return new ResponseEntity<>(new Response<>("No subscription"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                new Response<>("Success", null),
                HttpStatus.OK);
    }

    @GetMapping("/subscribers/list/{userToken}")
    public ResponseEntity<Response<List<String>>> getSubscriberList(@PathVariable String userToken) {
        UUID userUUID;
        try {
            userUUID = UUID.fromString(userToken);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new Response<>(null, "Bad token"),
                    HttpStatus.BAD_REQUEST);
        }

        Map<UUID, String> userTokens = Context.getInstance().getUserTokens();
        if (!userTokens.containsKey(userUUID)) {
            return new ResponseEntity<>(
                    new Response<>("User is not registered"),
                    HttpStatus.NOT_FOUND);
        }
        List<String> subscribers = Context.getInstance().getSubscriptions().entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue().contains(userUUID))
                .map(Map.Entry::getKey)
                .map(userTokens::get)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new Response<>(subscribers), HttpStatus.OK);
    }

    @GetMapping("/publishers/list/{userToken}")
    public ResponseEntity<Response<List<String>>> getPublisherList(@PathVariable String userToken) {
        UUID userUUID;
        try {
            userUUID = UUID.fromString(userToken);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new Response<>(null, "Bad token"),
                    HttpStatus.BAD_REQUEST);
        }

        Map<UUID, String> userTokens = Context.getInstance().getUserTokens();
        if (!userTokens.containsKey(userUUID)) {
            return new ResponseEntity<>(
                    new Response<>("User is not registered"),
                    HttpStatus.NOT_FOUND);
        }
        List<String> subscribers = Context.getInstance().getSubscriptions()
                .getOrDefault(userUUID, new ArrayList<>()).stream()
                .map(userTokens::get)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new Response<>(subscribers), HttpStatus.OK);
    }
}
