package ibf2023.paf.day25.services;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class Worker implements Runnable {

   private final RedisTemplate<String, String> template;
   private final String name;

   public Worker(RedisTemplate<String, String> template, String name) {
      this.template = template;
      this.name = name;
   }

   public void run() {
      System.out.println("*** Starting worker thread");

      ListOperations<String, String> listOps = template.opsForList();
      while (true)
         try {
            System.out.println("*** Queuing ");
            Optional<String> opt = Optional.ofNullable(listOps.rightPop("myqueue", Duration.ofSeconds(30)));
            if (opt.isEmpty())
               continue;
            String payload = opt.get();
            System.out.printf("*** %s Processing data: %s\n", name, payload);
         } catch (Exception ex) {
            System.err.printf(">>>> exception: %s\n", ex.getMessage());
         }
   }

}
