package ibf2023.paf.day25.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import ibf2023.paf.day25.constant.Constants;
import jakarta.json.Json;
import jakarta.json.JsonObject;

/* MessageService is another Spring service responsible for sending messages to Redis.
It injects the RedisTemplate (template) created earlier to interact with Redis.
The send() method constructs a JSON object (data) containing a message (msg) and a predefined 
ID (MY_ID), and then pushes this JSON string onto a Redis list (myqueue) using the 
RedisTemplate's opsForList() method. */

@Service
public class MessageService {

    @Autowired 
    @Qualifier("myredis")
    private RedisTemplate<String, String> template;

    public void send(String msg) {

        JsonObject data = Json.createObjectBuilder()
            .add("id", Constants.MY_ID)
            .add("message", msg)
            .build();

        ListOperations<String, String> listOps = template.opsForList();
        listOps.leftPush("myqueue", data.toString());
    }
}