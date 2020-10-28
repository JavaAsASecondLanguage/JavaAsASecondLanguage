package io.github.javaasasecondlanguage.flitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
//@RequestMapping("/api")
public class SampleController {
    HashMap<String, User> users = new HashMap<>();
    HashMap<String, User> tokens = new HashMap<>();
    ArrayList<Flit> flits = new ArrayList<>();
    private static final int NUM_FLITS = 10;

    @GetMapping("/greeting")
    String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("Invoked greeting: " + name);
        return "Hello " + name;
    }

    @DeleteMapping("/clear")
    ResponseEntity<?> clear() {
        users.clear();
        tokens.clear();
        flits.clear();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/register")
    ResponseEntity<?> register(@RequestBody String bodyJson) {
        try {
            String userName = new ObjectMapper().readValue(bodyJson, RegisterRequest.class).userName;
            if (users.containsKey(userName)) {
                return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(
                        new ErrorResponse(null, "This name is already taken")));
            } else {
                User newUser = new User(userName, UUID.randomUUID().toString());
                users.put(userName, newUser);
                tokens.put(newUser.getToken(), newUser);
                return ResponseEntity.ok(new ObjectMapper().writeValueAsString(new ErrorResponse(
                        new RegisterUser(newUser.getName(), newUser.getToken()), null)));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/list")
    ResponseEntity<?> listAllUsers() {
        try {
            return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(
                    new StringsResponse(new ArrayList<>(users.keySet()))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/flit/add")
    ResponseEntity<?> addFlit(@RequestBody String bodyJson) {
        try {
            FlitAddRequest req = new ObjectMapper().readValue(bodyJson, FlitAddRequest.class);
            if (tokens.containsKey(req.userToken)) {
                flits.add(new Flit(tokens.get(req.userToken).getName(),
                        tokens.get(req.userToken).getToken(), req.content));
                return ResponseEntity.ok(new ObjectMapper().writeValueAsString(new SingleStringResponse("ok")));
            }
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(
                    new ErrorResponse(null, "User not found")));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/flit/discover")
    ResponseEntity<?> discoverFlits() {
        try {
            return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(
                    new UserFlitResponse(flits.stream()
                            .skip(Integer.max(flits.size() - NUM_FLITS, 0))
                            .map(f -> new UserFlitData(f.getName()
                                    , f.getText())).collect(Collectors.toList()))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/flit/list/{username}")
    ResponseEntity<?> listFlitsUser(@PathVariable(value = "username") String username) {
        try {
            if (!users.containsKey(username))
                return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(
                        new ErrorResponse(null, "User not found")));
            return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(
                    new UserFlitResponse(flits.stream()
                            .filter(f -> f.getName().equals(username))
                            .map(f -> new UserFlitData(f.getName(), f.getText()))
                            .collect(Collectors.toList()))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/flit/list/feed/{usertoken}")
    ResponseEntity<?> listFeedUser(@PathVariable(value = "usertoken") String token) {
        try {
            if (tokens.containsKey(token)) {
                List<String> subscriptions = tokens.get(token).getSubscriptions();
                return ResponseEntity.ok().body(new ObjectMapper().writeValueAsString(
                        new UserFlitResponse(flits.stream()
                                .filter(f -> subscriptions.stream().anyMatch(s -> s.equals(f.getName())))
                                .map(f -> new UserFlitData(f.getName(), f.getText()))
                                .collect(Collectors.toList()))));
            }
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(
                    new ErrorResponse(null, "User not found")));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/subscribe")
    ResponseEntity<?> subscribe(@RequestBody String bodyJson) {
        try {
            var req = new ObjectMapper().readValue(bodyJson, SubscribeRequest.class);
            if (tokens.containsKey(req.subscriberToken)) {
                var user = tokens.get(req.subscriberToken);
                if (users.containsKey(req.publisherName)) {
                    user.getSubscriptions().add(req.publisherName);
                    users.get(req.publisherName).getFollowers().add(user.getName());
                    return ResponseEntity.ok(new ObjectMapper().writeValueAsString(
                            new SingleStringResponse("ok")));
                }
            }
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(
                    new ErrorResponse(null, "User not found")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/unsubscribe")
    ResponseEntity<?> unsubscribe(@RequestBody String bodyJson) {
        try {
            var req = new ObjectMapper().readValue(bodyJson, SubscribeRequest.class);
            if (tokens.containsKey(req.subscriberToken)) {
                var user = tokens.get(req.subscriberToken);
                if (user.getSubscriptions().contains(req.publisherName)) {
                    user.getSubscriptions().remove(req.publisherName);
                    users.get(req.publisherName).getFollowers().remove(user.getName());
                }
                return ResponseEntity.ok(new ObjectMapper().writeValueAsString(
                        new SingleStringResponse("ok")));
            }
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(
                    new ErrorResponse(null, "User not found")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/subscribers/list/{usertoken}")
    ResponseEntity<?> subscribersList(@PathVariable(value = "usertoken") String token) {
        try {
            if (tokens.containsKey(token))
                return ResponseEntity.ok(new ObjectMapper().writeValueAsString(
                        new StringsResponse(tokens.get(token).getFollowers())));
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(
                    new ErrorResponse(null, "User not found")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/publishers/list/{userToken}")
    ResponseEntity<?> publishersList(@PathVariable(value = "userToken") String token) {
        try {
            if (tokens.containsKey(token))
                return ResponseEntity.ok(new ObjectMapper().writeValueAsString(
                        new StringsResponse(tokens.get(token).getSubscriptions())));
            return ResponseEntity.badRequest().body(new ObjectMapper().writeValueAsString(
                    new ErrorResponse(null, "User not found")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
