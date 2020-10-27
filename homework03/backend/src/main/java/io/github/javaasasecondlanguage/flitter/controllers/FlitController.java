package io.github.javaasasecondlanguage.flitter.controllers;

import io.github.javaasasecondlanguage.flitter.FlitterStorage;
import io.github.javaasasecondlanguage.flitter.models.Flit;
import io.github.javaasasecondlanguage.flitter.models.Response;
import io.github.javaasasecondlanguage.flitter.requests.FlitAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flit")
public class FlitController {
    @Autowired
    private FlitterStorage storage;

    @PostMapping("/add")
    ResponseEntity<Response<String>> add(@RequestBody FlitAddRequest req) {
        var user = storage.authenticate(req.userToken);

        if (user != null) {
            storage.addFlit(user, req.content);

            return new ResponseEntity(
                    new Response("ok", null),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(
                    new Response("invalid token"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/discover")
    ResponseEntity<Response<List<Flit>>> discover() {
        var flits = storage.getLatestFlits(10);
        return new ResponseEntity(
                new Response(flits),
                HttpStatus.OK
        );
    }

    @GetMapping("/list/{userName}")
    ResponseEntity<Response<List<Flit>>> list(@PathVariable String userName) {
        var user = storage.getUser(userName);
        if (user != null) {
            var flits = storage.getUserFlits(user);
            return new ResponseEntity(
                    new Response(flits),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity(
                    new Response("user is not registered"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/list/feed/{userToken}")
    ResponseEntity<Response<List<Flit>>> list_feed(@PathVariable String userToken) {
        var user = storage.authenticate(userToken);
        if (user != null) {
            var flits = storage.getUserFeedFlits(user);
            return new ResponseEntity(
                    new Response(flits),
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
