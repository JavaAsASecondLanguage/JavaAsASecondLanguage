package io.github.javaasasecondlanguage.flitter;

import io.github.javaasasecondlanguage.flitter.pojos.Response;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilityController {

    @DeleteMapping("/clear")
    Response<String> clear() {
        Context.getInstance().clear();
        return new Response<>("Success", null);
    }
}
