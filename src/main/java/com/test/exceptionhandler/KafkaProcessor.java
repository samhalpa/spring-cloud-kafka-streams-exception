package com.test.exceptionhandler;

import com.test.exceptionhandler.model.InputDto;
import com.test.exceptionhandler.model.OutputDto;
import com.test.exceptionhandler.service.InputProcessorService;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@Slf4j
public class KafkaProcessor {

    private final Serde<InputDto> inputDtoSerde;

    @Autowired
    private InputProcessorService service;

    public KafkaProcessor() {
      this.inputDtoSerde = new JsonSerde<>(InputDto.class);
    }

    @Bean
    @SuppressWarnings("unchecked")
    public Function<KStream<String, InputDto>, KStream<String, OutputDto>> processor() {
        final AtomicReference<KeyValue<String, OutputDto>> result = new AtomicReference<>(null);
        return kStream -> kStream
                .filter((key, value) -> value != null)
                .peek((key, value) -> print(value))
                .filter((key, value) -> {
                  // below service.process() method throws a RuntimeException.
                    Optional<OutputDto> showOutputResult = service.process(value);
                    if (showOutputResult.isPresent()) {
                        result.set(new KeyValue<>(key, showOutputResult.get()));
                        return true;
                    }
                    return false;
                })
            .map((messageKey, messageValue) -> result.get());
    }

    private void print(InputDto value) {
        log.info("InternalDto: {}", value);
    }

}
