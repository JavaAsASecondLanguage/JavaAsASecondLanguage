package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.model.User;
import io.github.javaasasecondlanguage.flitter.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseBody
    ResponseEntity<?> register(@RequestBody User user) {
        User createdUser = userService.create(user);
        return BaseResponse.created(createdUser);
    }

    @GetMapping("/list")
    ResponseEntity<?> list() {
        List<User> users = userService.getAll();
        return BaseResponse.ok(users);
    }
}
