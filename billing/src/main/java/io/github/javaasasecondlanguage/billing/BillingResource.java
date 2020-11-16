package io.github.javaasasecondlanguage.billing;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Not broken implementation of billing service
 * Money are not lost here
 */
@Controller
@RequestMapping("billing")
public class BillingResource {
    class User {
        private final String name;
        private Integer money;
        User(String name, Integer money) {
            this.name = name;
            this.money = money;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", money=" + money +
                    '}';
        }
    }
    private final Map<String, User> userToMoney = new ConcurrentHashMap<>();
    /*
        statsLock is used to ensure that there is no one executing "/billing/stats" or
        the only one executing "/billing/stats" with no one executing other requests
     */
    private final ReadWriteLock statsLock = new ReentrantReadWriteLock();

    /**
     * curl -XPOST localhost:8080/billing/addUser -d "user=sasha&money=100000"
     * curl -XPOST localhost:8080/billing/addUser -d "user=sergey&money=100000"
     */
    @RequestMapping(
            path = "addUser",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> addUser(@RequestParam("user") String user,
                                          @RequestParam("money") Integer money) {
        statsLock.readLock().lock();
        if (user == null || money == null) {
            statsLock.readLock().unlock();
            return ResponseEntity.badRequest().body("");
        }

        if (userToMoney.containsKey(user)) {
            statsLock.readLock().unlock();
            return ResponseEntity.badRequest().body("User already exist");
        }
        userToMoney.put(user, new User(user, money));

        statsLock.readLock().unlock();
        return ResponseEntity.ok("Successfully created user [" + user + "] with money " + money + "\n");
    }

    /**
     * curl -XPOST localhost:8080/billing/sendMoney -d "from=sergey&to=sasha&money=1"
     */
    @RequestMapping(
            path = "sendMoney",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> sendMoney(@RequestParam("from") String fromUser,
                                            @RequestParam("to") String toUser,
                                            @RequestParam("money") Integer money) {
        statsLock.readLock().lock();
        if (fromUser == null || toUser == null || money == null) {
            statsLock.readLock().unlock();
            return ResponseEntity.badRequest().body("");
        }

        User from = null;
        User to = null;

        if (!userToMoney.containsKey(fromUser) || !userToMoney.containsKey(toUser)) {
            statsLock.readLock().unlock();
            return ResponseEntity.badRequest().body("No such user\n");
        }
        from = userToMoney.get(fromUser);
        to = userToMoney.get(toUser);

        User first = null;
        User second = null;
        if (from.name.compareTo(to.name) < 0) {
            first = from;
            second = to;
        } else {
            first = to;
            second = from;
        }

        synchronized (first) {
            synchronized (second) {
                if (from.money < money) {
                    statsLock.readLock().unlock();
                    return ResponseEntity.badRequest().body("Not enough money to send\n");
                }
                from.money -= money;
                to.money += money;
            }
        }

        statsLock.readLock().unlock();
        return ResponseEntity.ok("Send success\n");
    }

    /**
     * curl localhost:8080/billing/stat
     */
    @RequestMapping(
            path = "stat",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getStat() {
        statsLock.writeLock().lock();
        var result = ResponseEntity.ok(userToMoney + "\n");
        statsLock.writeLock().unlock();
        return result;
    }
}
