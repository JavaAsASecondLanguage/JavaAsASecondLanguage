package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.model.FlitModel;
import io.github.javaasasecondlanguage.flitter.base.FlitPut;
import io.github.javaasasecondlanguage.flitter.base.Response;
import io.github.javaasasecondlanguage.flitter.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flit")
public class FlitController {

    @PostMapping("add")
    ResponseEntity<?> addFlit(@RequestBody FlitPut flit) {
        if (UserModel.containsToken(flit.getUserToken())) {
            FlitModel.addFlit(flit.getUserToken(), flit.getContent());
            return ResponseEntity.ok(new Response<>("Success"));
        } else {
            return ResponseEntity.badRequest().body(new Response<>(null, "Token not found"));
        }
    }

    @GetMapping("discover")
    ResponseEntity<?> discoverFlit() {
        return ResponseEntity.ok(new Response<>(FlitModel.discover()));
    }

    @GetMapping("list/{userName}")
    ResponseEntity<?> list(@PathVariable String userName) {
        if (UserModel.containsUser(userName)) {
            return ResponseEntity.ok(new Response<>(FlitModel.getFlits(UserModel.getUserByName(userName))));
        } else {
            return ResponseEntity.badRequest().body(new Response<>(null, "User not found"));
        }
    }

    @GetMapping("list/feed/{userToken}")
    ResponseEntity<?> feed(@PathVariable String userToken) {
        if (UserModel.containsToken(userToken)) {
            return ResponseEntity.ok(new Response<>(FlitModel.getFeed(UserModel.getUserByToken(userToken))));
        } else {
            return ResponseEntity.badRequest().body(new Response<>(null, "User not found"));
        }
    }
}
