package io.github.javaasasecondlanguage.lecture01.practice1;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HelloWorldTest {

    @Test
    void getHelloWorld() {
        var hw = new HelloWorld();
        assertEquals("Hello, World!", hw.getHelloWorld());
    }
}