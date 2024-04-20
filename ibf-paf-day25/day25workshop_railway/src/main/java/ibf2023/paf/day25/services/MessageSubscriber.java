package ibf2023.paf.day25.services;

import java.io.StringReader;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

/* MessageSubscriber is a Spring service implementing MessageListener, which defines behavior 
for handling incoming Redis messages.

The onMessage() method is triggered when a message is received on a subscribed Redis channel 
(MY_TOPIC in this case).

It deserializes the message payload (expected to be a JSON string) into a JsonObject using the 
javax.json library and processes the message accordingly. */

@Service
public class MessageSubscriber implements MessageListener {

    @SuppressWarnings("null")
    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {

        String payload = new String(message.getBody());
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonObject data = reader.readObject();

        System.out.printf(">>>> message: %s\n", data.toString());
    }
}

