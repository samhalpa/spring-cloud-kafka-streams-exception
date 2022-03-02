package com.test.exceptionhandler.config;

import com.test.exceptionhandler.model.InputDto;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Test class to send messages to the topic
 */
@Configuration
public class MessageScheduler {

  @Autowired
  private KafkaTemplate kafkaTemplate;

  @Async
  @Scheduled(fixedRate = 5000)
  public void scheduleFixedRateTask() {
    int id = randomId(1, 1000000);
    kafkaTemplate.send("inTopic", new InputDto(id, "Key"+id, "value"+id ));

  }

  public int randomId(int min, int max) {
    Random random = new Random();
    return random.ints(min, max)
        .findFirst()
        .getAsInt();
  }
}
