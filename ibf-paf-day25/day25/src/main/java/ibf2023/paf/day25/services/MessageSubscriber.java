package ibf2023.paf.day25.services;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/* This applies when you want to do 1 to many, and this will be autowired into
the AppConfig's createMessageListener method.

This class is responsible for defining the behavior that will be executed when 
a message is received from the Redis channel. */

@Service
public class MessageSubscriber implements MessageListener {

	@SuppressWarnings("null")
	@Override
	public void onMessage(Message message, @Nullable byte[] pattern) {

		String data = new String(message.getBody());
		System.out.printf(">>>> message: %s\n", data);
		
	}
}
