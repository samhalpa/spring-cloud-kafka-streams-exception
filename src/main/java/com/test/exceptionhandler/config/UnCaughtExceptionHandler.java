package com.test.exceptionhandler.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UnCaughtExceptionHandler implements StreamsUncaughtExceptionHandler {

  @Autowired
  private StreamBridge streamBridge;

  @Override
  public StreamThreadExceptionResponse handle(Throwable exception) {
    log.error("Exception ", exception);
    // TODO handle exception
    return StreamThreadExceptionResponse.REPLACE_THREAD;
  }
}
