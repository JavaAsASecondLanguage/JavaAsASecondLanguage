package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.base.Response;
import io.github.javaasasecondlanguage.flitter.base.User;
import io.github.javaasasecondlanguage.flitter.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("register")
    ResponseEntity<?> register(@RequestBody User registerUser) {
        if (!UserModel.containsUser(registerUser.getUserName())) {
            UserModel.registerUser(registerUser.getUserName());
            return new ResponseEntity<>(
                    new Response<>(UserModel.getUserByName(registerUser.getUserName())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response<>(null, "This name is already taken"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("list")
    public ResponseEntity<?> getListUser() {
        return ResponseEntity.ok(new Response<>(UserModel.getUserList()));
    }
}