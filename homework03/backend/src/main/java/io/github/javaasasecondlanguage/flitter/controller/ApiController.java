package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.service.FlitService;
import io.github.javaasasecondlanguage.flitter.service.SubscriptionService;
import io.github.javaasasecondlanguage.flitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final FlitService flitService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public ApiController(FlitService flitService, UserService userService, SubscriptionService subscriptionService) {
        this.flitService = flitService;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
    }

    @DeleteMapping(
            path = "/clear",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<?> clear() {
        flitService.clear();
        userService.clear();
        subscriptionService.clear();
        return BaseResponse.empty();
    }
}
