package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.model.FlitModel;
import io.github.javaasasecondlanguage.flitter.model.UserModel;
import io.github.javaasasecondlanguage.flitter.storage.Context;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("flit")
public class FlitController {
    @RequestMapping(
            path = "add",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> addFlit(@RequestBody FlitModel flit) {
        var errMsg = Context.getInstance().addFlit(flit);
        if (errMsg.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(ControllerUtils.makeResponse(null, errMsg.get()));
        }
        return ResponseEntity.ok(ControllerUtils.makeResponse("success", null));
    }

    @RequestMapping(
            path = "discover",
            method = RequestMethod.GET
    )
    ResponseEntity<?> discoverFlits() {
        return ResponseEntity
                .ok(ControllerUtils.makeResponse(Context.getInstance().discoverFlits(), null));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "list/{userName}"
    )
    ResponseEntity<?> listFlits(@PathVariable String userName) {
        var result = Context.getInstance().flitsOf(userName);
        if (result == null) {
            return ResponseEntity.badRequest()
                    .body(ControllerUtils.makeResponse(null, "Unknown username"));
        }
        return ResponseEntity.ok(ControllerUtils.makeResponse(result, null));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "list/feed/{userToken}"
    )
    ResponseEntity<?> listFeedFlits(@PathVariable String userToken) {
        var result = Context.getInstance().getFeed(userToken);
        if (result == null) {
            return ResponseEntity.badRequest()
                    .body(ControllerUtils.makeResponse(null, "Unknown userToken"));
        }
        return ResponseEntity.ok(ControllerUtils.makeResponse(result, null));
    }
}
