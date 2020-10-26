package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.dto.AddFlitDto;
import io.github.javaasasecondlanguage.flitter.service.FlitService;
import io.github.javaasasecondlanguage.flitter.service.UserService;
import io.github.javaasasecondlanguage.flitter.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flit")
public class FlitController {

    private final FlitService flitService;
    private final UserService userService;

    @Autowired
    public FlitController(FlitService flitService, UserService userService) {
        this.flitService = flitService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AddFlitDto addFlitDto) {
        var authUser = userService.getUserByToken(addFlitDto.getUserToken());
        var createdFlit = flitService.create(authUser, Mapper.toFlit(addFlitDto));
        return BaseResponse.ok(createdFlit);
    }

    @GetMapping("/discover")
    public ResponseEntity<?> discover() {
        var lastFlits = flitService.getLastFlits(10);
        return BaseResponse.ok(lastFlits);
    }

    @GetMapping("/list/{username}")
    public ResponseEntity<?> findAllByUser(@PathVariable String username) {
        var flits = flitService.getFlitsByPublisherName(username);
        return BaseResponse.ok(flits);
    }

    @GetMapping("/list/feed/{usertoken}")
    public ResponseEntity<?> findFeed(@PathVariable String usertoken) {
        var authUser = userService.getUserByToken(usertoken);
        var flits = flitService.getFlitsBySubscriber(authUser);
        return BaseResponse.ok(flits);
    }
}
