package io.github.javaasasecondlanguage.flitter.controllers;

import io.github.javaasasecondlanguage.flitter.FlitterStorage;
import io.github.javaasasecondlanguage.flitter.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClearController {
    @Autowired
    private FlitterStorage storage;

    @DeleteMapping("/clear")
    Response<String> clear() {
        storage.clear();
        return new Response("ok");
    }
}
