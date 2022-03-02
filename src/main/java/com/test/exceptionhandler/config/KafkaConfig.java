package com.test.exceptionhandler.config;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Slf4j
@Configuration
public class KafkaConfig {

    private String inTopic = "inTopic";

    private String outTopic = "outTopic";

    private String errorTopic = "errorTopic";

    private int replicationFactor = 1;

    private int partitionCount = 2;

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    UnCaughtExceptionHandler exceptionHandler;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>(kafkaProperties.buildAdminProperties());
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaAdmin.NewTopics createNewTopics() {
        return new KafkaAdmin.NewTopics(
            new NewTopic(inTopic, partitionCount, (short) replicationFactor),
            new NewTopic(outTopic, partitionCount, (short) replicationFactor),
            new NewTopic(errorTopic, partitionCount, (short) replicationFactor)
        );
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>(kafkaProperties.buildProducerProperties());
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public StreamsBuilderFactoryBeanConfigurer streamsCustomizer() {
        return new StreamsBuilderFactoryBeanConfigurer() {

            @Override
            public void configure(StreamsBuilderFactoryBean factoryBean) {;
                factoryBean.setStreamsUncaughtExceptionHandler(exceptionHandler);
            }

            @Override
            public int getOrder() {
                return Integer.MAX_VALUE - 10000;
            }

        };
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        final ObjectMapper mapper = new ObjectMapper();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
//        mapper.setDateFormat(df);
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
//        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//        mapper.setDefaultSetterInfo(JsonSetter.Value.forContentNulls(Nulls.AS_EMPTY));
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
//
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
//        mapper.registerModule(javaTimeModule);
//        return mapper;
//    }
}
