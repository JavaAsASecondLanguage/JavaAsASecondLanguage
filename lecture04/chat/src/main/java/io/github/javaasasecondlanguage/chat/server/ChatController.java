package io.github.javaasasecondlanguage.chat.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Controller
@RequestMapping("chat")
public class ChatController {
    private final Queue<String> messages = new ConcurrentLinkedQueue<>();
    private final Map<String, String> usersOnline = new ConcurrentHashMap<>();

    /**
     * curl -X POST -i localhost:8080/chat/login -d "name=I_CAN_COPY_PASTE"
     */
    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("name") String name) {
        if (name.length() < 1) {
            return ResponseEntity.badRequest().body("Too short name, sorry :(");
        }
        if (name.length() > 20) {
            return ResponseEntity.badRequest().body("Too long name, sorry :(");
        }
        if (usersOnline.containsKey(name)) {
            return ResponseEntity.badRequest().body("Already logged in:(");
        }
        usersOnline.put(name, name);
        messages.add("[" + name + "] logged in");
        return ResponseEntity.ok().build();
    }

    /**
     * curl -i localhost:8080/chat/online
     */
    @RequestMapping(
            path = "online",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<?> online() {
        String responseBody = usersOnline.keySet().stream()
                .sorted()
                .collect(Collectors.joining("\n"));
        return ResponseEntity.ok(responseBody);
    }

    /**
     * curl -X POST -i localhost:8080/chat/logout -d "name=I_CAN_COPY_PASTE"
     */
    @RequestMapping(
            path = "logout",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> logout(@RequestParam("name") String name) {
        usersOnline.remove(name);
        messages.add("[" + name + "] logged out");
        return ResponseEntity.ok().build();
    }

    /**
     * curl -X POST -i localhost:8080/chat/say -d "name=I_CAN_COPY_PASTE&msg=Hello"
     */
    @RequestMapping(
            path = "say",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> say(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        usersOnline.remove(name);
        messages.add("[" + name + "] said [" + msg);
        return ResponseEntity.ok().build();
    }

    /**
     * curl -i localhost:8080/chat/chat
     */
    @RequestMapping(
            path = "chat",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> chat(@RequestParam("name") String name, @RequestParam("msg") String msg) {
        usersOnline.remove(name);
        messages.add("[" + name + "] said [" + msg);
        return ResponseEntity.ok().build();
    }
}
