package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.pojos.Flit;
import io.github.javaasasecondlanguage.flitter.pojos.FlitRegisterRequest;
import io.github.javaasasecondlanguage.flitter.pojos.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("flit")
public class FlitController {

    @PostMapping("add")
    ResponseEntity<Response<String>> addFlit(@RequestBody FlitRegisterRequest registerRequest) {
        UUID userUUID;
        try {
            userUUID = UUID.fromString(registerRequest.getUserToken());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new Response<>(null, "Bad token"),
                    HttpStatus.BAD_REQUEST);
        }

        Map<UUID, String> userTokens = Context.getInstance().getUserTokens();
        String userName = userTokens.get(userUUID);
        if (userName == null) {
            return new ResponseEntity<>(
                    new Response<>(null, "User is not registered"),
                    HttpStatus.NOT_FOUND);
        }
        Context.getInstance().getFlits().add(
                new Flit(userName, registerRequest.getContent()));
        return new ResponseEntity<>(
                new Response<>("Success", null),
                HttpStatus.OK);
    }

    @GetMapping("discover")
    Response<List<Flit>> discover() {
        List<Flit> flits = Context.getInstance().getFlits();
        int flitNumber = flits.size();
        int flitOffset = Math.max(0, flitNumber - 10);
        return new Response<>(flits.subList(flitOffset, flitNumber));
    }

    @GetMapping("list/{userName}")
    ResponseEntity<Response<List<Flit>>> list(@PathVariable String userName) {
        if (!Context.getInstance().getUserTokens().containsValue(userName)) {
            return new ResponseEntity<>(
                    new Response<>("User is not registered"),
                    HttpStatus.NOT_FOUND);
        }
        List<Flit> flits = Context.getInstance().getFlits().stream()
                .filter(f -> f.getUserName().equals(userName))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new Response<>(flits), HttpStatus.OK);
    }

    @GetMapping("list/feed/{userToken}")
    ResponseEntity<Response<List<Flit>>> feed(@PathVariable String userToken) {
        UUID userUUID;
        try {
            userUUID = UUID.fromString(userToken);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    new Response<>(null, "Bad token"),
                    HttpStatus.BAD_REQUEST);
        }

        Map<UUID, String> userTokens = Context.getInstance().getUserTokens();
        if (!userTokens.containsKey(userUUID)) {
            return new ResponseEntity<>(
                    new Response<>("User is not registered"),
                    HttpStatus.NOT_FOUND);
        }
        List<String> publishers = Context.getInstance().getSubscriptions()
                .getOrDefault(userUUID, new ArrayList<>())
                .stream().map(userTokens::get).collect(Collectors.toList());
        List<Flit> flits = Context.getInstance().getFlits().stream()
                .filter(f -> publishers.contains(f.getUserName()))
                .filter(f -> !f.getSeenBy().contains(userUUID))
                .collect(Collectors.toList());
        for (Flit flit : flits) {
            flit.getSeenBy().add(userUUID);
        }
        return new ResponseEntity<>(new Response<>(flits), HttpStatus.OK);
    }
}
