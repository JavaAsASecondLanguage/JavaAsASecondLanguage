package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.model.FlitModel;
import io.github.javaasasecondlanguage.flitter.base.Response;
import io.github.javaasasecondlanguage.flitter.model.SubscribeModel;
import io.github.javaasasecondlanguage.flitter.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClearController {

    @RequestMapping(path = "clear", method = RequestMethod.DELETE)
    public ResponseEntity<?> clear() {

        UserModel.clear();
        FlitModel.clear();
        SubscribeModel.clear();

        return ResponseEntity.ok(new Response<>("Success"));
    }

}
