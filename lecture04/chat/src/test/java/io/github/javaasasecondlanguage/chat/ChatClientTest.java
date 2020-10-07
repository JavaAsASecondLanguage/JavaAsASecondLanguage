package io.github.javaasasecondlanguage.chat;

import io.github.javaasasecondlanguage.chat.client.ChatClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatClientTest {
    private static final String MY_NAME_IN_CHAT = "I_CAN_COPY_PASTE";
    private static final String MY_MESSAGE_TO_CHAT = "SOMEONE_KILL_ME";

    @Test
    public void login() throws IOException, InterruptedException {
        var response = ChatClient.login(MY_NAME_IN_CHAT);
        System.out.println("[" + response + "]");
        String body = response.body();
        System.out.println();
        assertTrue(response.statusCode() == 200 || body.equals("Already logged in:("));
    }

    @Test
    public void viewChat() throws IOException, InterruptedException {
        var response = ChatClient.viewChat();
        System.out.println("[" + response + "]");
        System.out.println(response.body());
        assertEquals(200, response.statusCode());
    }


    @Test
    public void viewOnline() throws IOException, InterruptedException {
        var response = ChatClient.viewOnline();
        System.out.println("[" + response + "]");
        System.out.println(response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    public void say() throws IOException, InterruptedException {
        var response = ChatClient.say(MY_NAME_IN_CHAT, MY_MESSAGE_TO_CHAT);
        System.out.println("[" + response + "]");
        System.out.println(response.body());
        assertEquals(200, response.statusCode());
    }
}
