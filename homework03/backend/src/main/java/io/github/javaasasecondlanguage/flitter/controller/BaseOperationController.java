package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.storage.Context;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseOperationController {
    @RequestMapping(
            path = "clear",
            method = RequestMethod.DELETE
    )
    ResponseEntity<?> clear() {
        Context.recreateInstance();
        return ResponseEntity.ok("Ok");
    }
}
