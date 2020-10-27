package io.github.javaasasecondlanguage.flitter.controllers;

import io.github.javaasasecondlanguage.flitter.FlitterStorage;
import io.github.javaasasecondlanguage.flitter.models.Response;
import io.github.javaasasecondlanguage.flitter.models.User;
import io.github.javaasasecondlanguage.flitter.requests.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private FlitterStorage storage;

    @GetMapping("/list")
    Response<List<String>> list() {
        return new Response(
                storage.listUsers()
                        .stream()
                        .map((u) -> u.name)
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/register")
    ResponseEntity<Response<User>> register(@RequestBody UserRegisterRequest req) {
        var user = storage.registerUser(req.userName);

        if (user != null) {
            return new ResponseEntity(new Response(user), HttpStatus.OK);
        } else {
            return new ResponseEntity(
                    new Response("This name is already taken"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
