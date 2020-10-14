package io.github.javaasasecondlanguage.flitter.controller;

import io.github.javaasasecondlanguage.flitter.model.SubscriptionModel;
import io.github.javaasasecondlanguage.flitter.storage.Context;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionController {
    @RequestMapping(
            path = "subscribe",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionModel subscriptionModel) {
        var errMsg = Context.getInstance().subscribeTo(subscriptionModel);
        if (errMsg.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(ControllerUtils.makeResponse(null, errMsg.get()));
        }
        return ResponseEntity.ok(ControllerUtils.makeResponse("success", null));
    }

    @RequestMapping(
            path = "unsubscribe",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> unsubscribe(@RequestBody SubscriptionModel subscriptionModel) {
        var errMsg = Context.getInstance().unsubscribeTo(subscriptionModel);
        if (errMsg.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(ControllerUtils.makeResponse(null, errMsg.get()));
        }
        return ResponseEntity.ok(ControllerUtils.makeResponse("success", null));
    }

    @RequestMapping(
            value = "subscribers/list/{userToken}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> listSubscribers(@PathVariable String userToken) {
        var result = Context.getInstance().getSubscribers(userToken);
        if (result == null) {
            return ResponseEntity.badRequest()
                    .body(ControllerUtils.makeResponse(null, "User not found"));
        }
        return ResponseEntity.ok(ControllerUtils.makeResponse(result, null));
    }

    @RequestMapping(
            value = "publishers/list/{userToken}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> listPublishers(@PathVariable String userToken) {
        var result = Context.getInstance().getPublishers(userToken);
        if (result == null) {
            return ResponseEntity.badRequest()
                    .body(ControllerUtils.makeResponse(null, "User not found"));
        }
        return ResponseEntity.ok(ControllerUtils.makeResponse(result, null));
    }
}
