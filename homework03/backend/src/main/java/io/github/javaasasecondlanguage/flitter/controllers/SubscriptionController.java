package io.github.javaasasecondlanguage.flitter.controllers;

import io.github.javaasasecondlanguage.flitter.FlitterStorage;
import io.github.javaasasecondlanguage.flitter.models.Flit;
import io.github.javaasasecondlanguage.flitter.models.Response;
import io.github.javaasasecondlanguage.flitter.requests.FlitAddRequest;
import io.github.javaasasecondlanguage.flitter.requests.SubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SubscriptionController {
    @Autowired
    private FlitterStorage storage;

    @PostMapping("/subscribe")
    ResponseEntity<Response<String>> subscribe(@RequestBody SubscriptionRequest req) {
        var subscriber = storage.authenticate(req.subscriberToken);
        if (subscriber != null) {
            var publisher = storage.getUser(req.publisherName);
            if (publisher != null) {
                storage.subscribe(subscriber, publisher);

                return new ResponseEntity(
                        new Response("ok", null),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity(
                        new Response("publisher user is not found"),
                        HttpStatus.BAD_REQUEST
                );
            }
        } else {
            return new ResponseEntity(
                    new Response("invalid token"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/unsubscribe")
    ResponseEntity<Response<String>> unsubscribe(@RequestBody SubscriptionRequest req) {
        var subscriber = storage.authenticate(req.subscriberToken);
        if (subscriber != null) {
            var publisher = storage.getUser(req.publisherName);
            if (publisher != null) {
                storage.unsubscribe(subscriber, publisher);

                return new ResponseEntity(
                        new Response("ok", null),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity(
                        new Response("publisher user is not found"),
                        HttpStatus.BAD_REQUEST
                );
            }
        } else {
            return new ResponseEntity(
                    new Response("invalid token"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/subscribers/list/{userToken}")
    ResponseEntity<Response<List<String>>> list_subscribers(@PathVariable String userToken) {
        var user = storage.authenticate(userToken);
        if (user != null) {
            var subscribers = storage.getSubscribers(user);
            return new ResponseEntity(
                    new Response(subscribers.stream().map(u -> u.name).collect(Collectors.toList())),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(
                    new Response("user is not registered"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/publishers/list/{userToken}")
    ResponseEntity<Response<List<String>>> list_publishers(@PathVariable String userToken) {
        var user = storage.authenticate(userToken);
        if (user != null) {
            var subscribers = storage.getPublishers(user);
            return new ResponseEntity(
                    new Response(subscribers.stream().map(u -> u.name).collect(Collectors.toList())),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(
                    new Response("user is not registered"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
