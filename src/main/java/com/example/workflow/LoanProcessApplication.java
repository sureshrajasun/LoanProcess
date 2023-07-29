package com.example.workflow;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.spring.SpringTopicSubscription;
import org.camunda.bpm.client.spring.event.SubscriptionInitializedEvent;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableProcessApplication("Loan_Process_2")
public class LoanProcessApplication {

  protected static Logger LOG = LoggerFactory.getLogger(LoanProcessApplication.class);
  public static void main(String... args) {
    SpringApplication.run(LoanProcessApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }

/*  @Bean
  public ExternalTaskClient customClient() {
    return ExternalTaskClient.create()
            .baseUrl("http://localhost:8282/engine-rest")
            .build();
  }*/

  @EventListener(SubscriptionInitializedEvent.class)
  public void catchSubscriptionInitEvent(SubscriptionInitializedEvent event) {

    SpringTopicSubscription topicSubscription = event.getSource();
    if (!topicSubscription.isAutoOpen()) {

      // open topic in case it is not opened already
      topicSubscription.open();

      LOG.info("Subscription with topic name '{}' has been opened!",
              topicSubscription.getTopicName());
    }
  }
}