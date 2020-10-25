package io.github.javaasasecondlanguage.flitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

@RestController
public class SampleController {
    private ConcurrentLinkedQueue<User> users = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedDeque<Flit> flits = new ConcurrentLinkedDeque<>();
    private ConcurrentLinkedDeque<Subscription> subscriptions = new ConcurrentLinkedDeque<>();
    private Semaphore flitsSem = new Semaphore(1);

    @GetMapping("/api/greeting")
    String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("Invoked greeting: " + name);
        return "Hello " + name;
    }

    @DeleteMapping("/clear")
    ResponseEntity<?> clear() {
        System.out.println("clear = {}");
        users.clear();
        flits.clear();
        subscriptions.clear();
        flitsSem = new Semaphore(1);
        System.out.println("resp = {}");
        return ResponseEntity.ok("");
    }

    @PostMapping("/user/register")
    ResponseEntity<?> register(@RequestBody String jsonBody) {
        System.out.println("register = {.jsonBody = '" + jsonBody + "'}");
        try {
            if (jsonBody == null) {
                RegisterResponse resp = new RegisterResponse(null, "JSON body is null");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.badRequest().body(outputJSON);
            }

            RegisterRequest req = new ObjectMapper().readValue(jsonBody, RegisterRequest.class);
            if (!users.stream().noneMatch(user1 -> user1.getName().equals(req.userName))) {
                RegisterResponse resp = new RegisterResponse(null, "This name is already taken");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.badRequest().body(outputJSON);
            }

            User user = new User(req.userName);
            this.users.add(user);

            RegisterResponseData data = new RegisterResponseData(user.getName(), user.getToken());
            RegisterResponse resp = new RegisterResponse(data, null);
            String outputJSON = new ObjectMapper().writeValueAsString(resp);
            System.out.println("resp = {'" + outputJSON + "'}");
            return ResponseEntity.ok(outputJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/list")
    ResponseEntity<?> userList() {
        System.out.println("userList = {}");
        try {
            LinkedList<String> userNamesList = new LinkedList<>();
            for (User u : this.users) {
                userNamesList.add(u.name);
            }

            UserListResponse resp = new UserListResponse(userNamesList);
            String outputJSON = new ObjectMapper().writeValueAsString(resp);
            System.out.println("resp = {'" + outputJSON + "'}");
            return ResponseEntity.ok().body(outputJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/flit/add")
    ResponseEntity<?> flitAdd(@RequestBody String jsonBody) {
        System.out.println("flitAdd = {.jsonBody = '" + jsonBody + "'}");
        try {
            if (jsonBody == null) {
                RegisterResponse resp = new RegisterResponse(null, "JSON body is null");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.badRequest().body(outputJSON);
            }

            FlitAddRequest req = new ObjectMapper().readValue(jsonBody, FlitAddRequest.class);
            if (this.users.stream().noneMatch(user -> user.token.equals(req.userToken))) {
                RegisterResponse resp = new RegisterResponse(null, "User token not found");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(outputJSON);
            }

            String userName = this.users.stream().filter(user -> user.token.equals(req.userToken)).findFirst().orElseThrow().getName();

            flitsSem.acquire();
            this.flits.addLast(new Flit(userName, req.userToken, req.content));
            flitsSem.release();

            FlitAddResponse resp = new FlitAddResponse("ok");
            String outputJSON = new ObjectMapper().writeValueAsString(resp);
            System.out.println("resp = {'" + outputJSON + "'}");
            return ResponseEntity.ok().body(outputJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/flit/discover")
    ResponseEntity<?> flitDiscover() {
        System.out.println("flitDiscover = {}");

        try {
            flitsSem.acquire();

            LinkedList<FlitDiscoverPart> flitDiscoverParts = new LinkedList<FlitDiscoverPart>();
            LinkedList<Flit> last10Flits = new LinkedList<Flit>();
            for (int i = 0; i < 10 && !this.flits.isEmpty(); i++) {
                Flit curFlit = this.flits.getLast();
                flitDiscoverParts.addFirst(new FlitDiscoverPart(curFlit.userName, curFlit.content));
                last10Flits.addFirst(curFlit);
                this.flits.removeLast();
            }

            FlitDiscoverResponse resp = new FlitDiscoverResponse(flitDiscoverParts);
            String outputJSON = new ObjectMapper().writeValueAsString(resp);
            System.out.println("resp = {'" + outputJSON + "'}");

            for (Flit flit : last10Flits) {
                this.flits.addLast(flit);
            }

            flitsSem.release();
            return ResponseEntity.ok().body(outputJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/flit/list/{username}")
    ResponseEntity<?> flitList(@PathVariable("username") String userName) {
        System.out.println("flitDiscover = {" + userName + "}");
        try {
            if (this.users.stream().noneMatch(user -> user.name.equals(userName))) {
                FlitListResponse resp = new FlitListResponse(null, "User not found");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.badRequest().body(resp);
            }

            flitsSem.acquire();
            List<Flit> usersFlits = this.flits.stream().filter(flit -> flit.userName.equals(userName)).collect(Collectors.toList());
            LinkedList<FlitListPart> flitListParts = new LinkedList<>();
            for (Flit flit : usersFlits) {
                flitListParts.addLast(new FlitListPart(flit.userName, flit.content));
            }

            FlitListResponse resp = new FlitListResponse(flitListParts, null);
            String outputJSON = new ObjectMapper().writeValueAsString(resp);
            System.out.println("resp = {'" + outputJSON + "'}");

            flitsSem.release();
            return ResponseEntity.ok().body(outputJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/subscribe")
    ResponseEntity<?> subscribe(@RequestBody String jsonBody) {
        System.out.println("subscribe = {.jsonBody = '" + jsonBody + "'}");
        try {
            if (jsonBody == null) {
                SubscribeResponse resp = new SubscribeResponse(null, "JSON body is null");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.badRequest().body(outputJSON);
            }

            SubscribeRequest req = new ObjectMapper().readValue(jsonBody, SubscribeRequest.class);
            if (this.users.stream().noneMatch(user -> user.token.equals(req.subscriberToken))) {
                RegisterResponse resp = new RegisterResponse(null, "User token not found");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(outputJSON);
            }
            if (this.users.stream().noneMatch(user -> user.name.equals(req.publisherName))) {
                RegisterResponse resp = new RegisterResponse(null, "User name not found");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(outputJSON);
            }

            String subscriberName = this.users.stream().filter(user -> user.token.equals(req.subscriberToken)).findAny().orElseThrow().name;
            this.subscriptions.addLast(new Subscription(subscriberName, req.publisherName));

            SubscribeResponse resp = new SubscribeResponse("ok", null);
            String outputJSON = new ObjectMapper().writeValueAsString(resp);
            System.out.println("resp = {'" + outputJSON + "'}");
            return ResponseEntity.ok().body(outputJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/unsubscribe")
    ResponseEntity<?> unsubscribe(@RequestBody String jsonBody) {
        System.out.println("unsubscribe = {.jsonBody = '" + jsonBody + "'}");
        try {
            if (jsonBody == null) {
                SubscribeResponse resp = new SubscribeResponse(null, "JSON body is null");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.badRequest().body(outputJSON);
            }

            SubscribeRequest req = new ObjectMapper().readValue(jsonBody, SubscribeRequest.class);
            if (this.users.stream().noneMatch(user -> user.token.equals(req.subscriberToken))) {
                RegisterResponse resp = new RegisterResponse(null, "User token not found");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(outputJSON);
            }
            if (this.users.stream().noneMatch(user -> user.name.equals(req.publisherName))) {
                RegisterResponse resp = new RegisterResponse(null, "User name not found");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(outputJSON);
            }

            String subscriberName = this.users.stream().filter(user -> user.token.equals(req.subscriberToken)).findAny().orElseThrow().name;
            this.subscriptions.removeIf(subscription -> subscription.publisherName.equals(req.publisherName) && subscription.subscriberName.equals(subscriberName));

            SubscribeResponse resp = new SubscribeResponse("ok", null);
            String outputJSON = new ObjectMapper().writeValueAsString(resp);
            System.out.println("resp = {'" + outputJSON + "'}");
            return ResponseEntity.ok().body(outputJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/subscribers/list/{usertoken}")
    ResponseEntity<?> subscribersList(@PathVariable("usertoken") String userToken) {
        System.out.println("subscribersList = {" + userToken + "}");
        try {
            if (this.users.stream().noneMatch(user -> user.token.equals(userToken))) {
                SubscribersListResponse resp = new SubscribersListResponse(null, "User token not found");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.badRequest().body(resp);
            }

            String userName = this.users.stream().filter(user -> user.token.equals(userToken)).findAny().orElseThrow().name;
            List<String> subscribers = this.subscriptions.stream()
                    .filter(subscription -> subscription.publisherName.equals(userName))
                    .map(subscription -> subscription.subscriberName)
                    .collect(Collectors.toList());

            SubscribersListResponse resp = new SubscribersListResponse(subscribers, null);
            String outputJSON = new ObjectMapper().writeValueAsString(resp);
            System.out.println("resp = {'" + outputJSON + "'}");
            return ResponseEntity.ok().body(outputJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/publishers/list/{usertoken}")
    ResponseEntity<?> publishersList(@PathVariable("usertoken") String userToken) {
        System.out.println("publishersList = {" + userToken + "}");
        try {
            if (this.users.stream().noneMatch(user -> user.token.equals(userToken))) {
                SubscribersListResponse resp = new SubscribersListResponse(null, "User token not found");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.badRequest().body(resp);
            }

            String userName = this.users.stream().filter(user -> user.token.equals(userToken)).findAny().orElseThrow().name;
            List<String> subscribers = this.subscriptions.stream()
                    .filter(subscription -> subscription.subscriberName.equals(userName))
                    .map(subscription -> subscription.publisherName)
                    .collect(Collectors.toList());

            SubscribersListResponse resp = new SubscribersListResponse(subscribers, null);
            String outputJSON = new ObjectMapper().writeValueAsString(resp);
            System.out.println("resp = {'" + outputJSON + "'}");
            return ResponseEntity.ok().body(outputJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/flit/list/feed/{usertoken}")
    ResponseEntity<?> flitListFeed(@PathVariable("usertoken") String userToken) {
        System.out.println("flitListFeed = {" + userToken + "}");
        try {
            if (this.users.stream().noneMatch(user -> user.token.equals(userToken))) {
                FlitListResponse resp = new FlitListResponse(null, "User not found");
                String outputJSON = new ObjectMapper().writeValueAsString(resp);
                System.out.println("resp = {'" + outputJSON + "'}");
                return ResponseEntity.badRequest().body(resp);
            }

            String userName = this.users.stream().filter(user -> user.token.equals(userToken)).findAny().orElseThrow().name;
            List<String> publishers = this.subscriptions.stream()
                    .filter(subscription -> subscription.subscriberName.equals(userName))
                    .map(subscription -> subscription.publisherName)
                    .collect(Collectors.toList());

            flitsSem.acquire();
            List<Flit> usersFlits = this.flits.stream().filter(flit -> publishers.contains(flit.userName)).collect(Collectors.toList());
            LinkedList<FlitListPart> flitListParts = new LinkedList<>();
            for (Flit flit : usersFlits) {
                flitListParts.addLast(new FlitListPart(flit.userName, flit.content));
            }

            FlitListResponse resp = new FlitListResponse(flitListParts, null);
            String outputJSON = new ObjectMapper().writeValueAsString(resp);
            System.out.println("resp = {'" + outputJSON + "'}");

            flitsSem.release();
            return ResponseEntity.ok().body(outputJSON);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
