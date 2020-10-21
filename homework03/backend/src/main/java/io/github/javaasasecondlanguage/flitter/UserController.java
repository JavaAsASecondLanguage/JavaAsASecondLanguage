package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.pojos.Response;
import io.github.javaasasecondlanguage.flitter.pojos.User;
import io.github.javaasasecondlanguage.flitter.pojos.UserRegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/register")
    ResponseEntity<Response<User>> register(@RequestBody UserRegisterRequest registerRequest) {
        var tokens = Context.getInstance().getUserTokens();
        if (!tokens.containsValue(registerRequest.getUserName())) {
            User newUser = new User(registerRequest.getUserName(), UUID.randomUUID());
            tokens.put(newUser.getUserToken(), registerRequest.getUserName());
            return new ResponseEntity<>(new Response<>(newUser), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new Response<>("This name is already taken"),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/list")
    Response<List<String>> list() {
        return new Response<>(
                new ArrayList<>(Context.getInstance().getUserTokens().values()));
    }
}
