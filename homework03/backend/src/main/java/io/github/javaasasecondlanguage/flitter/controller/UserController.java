package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.model.UserModel;
import io.github.javaasasecondlanguage.flitter.storage.Context;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {
    @RequestMapping(
            path = "register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> addUser(@RequestBody UserModel user) {
        var result = Context.getInstance().registerUser(user.userName());
        if (result.isPresent()) {
            return  ResponseEntity.ok(ControllerUtils.makeResponse(result.get(), null));
        } else {
            return ResponseEntity.badRequest()
                    .body(ControllerUtils.makeResponse(null, "User with such name already exists"));
        }
    }

    @RequestMapping(
            path = "list",
            method = RequestMethod.GET
    )
    ResponseEntity<?> listUsers() {
        return ResponseEntity
                .ok(ControllerUtils.makeResponse(Context.getInstance().getUsers(), null));
    }
}
