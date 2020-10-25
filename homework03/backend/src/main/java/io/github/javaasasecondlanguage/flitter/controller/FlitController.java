package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.model.Flit;
import io.github.javaasasecondlanguage.flitter.service.FlitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flit")
public class FlitController {

    private final FlitService flitService;

    @Autowired
    public FlitController(FlitService flitService) {
        this.flitService = flitService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Flit flit) {

        var createdFlit = flitService.create(flit);
        return BaseResponse.created(createdFlit);
    }

    @GetMapping("/discover")
    public ResponseEntity<?> discover() {
        var lastFlits = flitService.getLastFlits(10);
        return BaseResponse.ok(lastFlits);
    }

    @GetMapping("list/{username}")
    public ResponseEntity<?> findAllByUser(@RequestParam String username) {
        var flits = flitService.getFlitsByUserName(username);
        return BaseResponse.ok(flits);
    }

    @GetMapping("/flit/list/feed/{usertoken}")
    public ResponseEntity<?> findFeed(@RequestParam String userToken) {
        var flits = flitService.getFlitsByUserToken(userToken);
        return BaseResponse.ok(flits);
    }


}
