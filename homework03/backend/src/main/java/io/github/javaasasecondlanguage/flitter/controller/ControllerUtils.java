package io.github.javaasasecondlanguage.flitter.controller;

import java.util.HashMap;
import java.util.Map;

public class ControllerUtils {
    public static <T> Map<String, Object> makeResponse(T data, String error) {
        var map = new HashMap<String, Object>();
        if (data != null) {
            map.put("data", data);
        }
        if (error != null) {
            map.put("errorMessage", error);
        }
        return map;
    }
}
