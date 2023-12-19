package guru.springframework.msscbeerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TOPIC_EXCHANGE_NAME = "beer-exchange";
    public static final String BREWING_QUEUE = "brewing-request";
    public static final String NEW_INVENTORY_QUEUE = "new-inventory-request";
    public static final String BREWING_ROUTING_KEY = "brewing.#";
    public static final String NEW_INVENTORY_ROUTING_KEY = "inventory.#";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Queue brewingQueue() {
        return new Queue(BREWING_QUEUE, false);
    }

    @Bean
    Queue newInventoryQueue() {
        return new Queue(NEW_INVENTORY_QUEUE, false);
    }

    @Bean
    Binding bindingBrewing(Queue brewingQueue, TopicExchange exchange) {
        return BindingBuilder.bind(brewingQueue).to(exchange).with(BREWING_ROUTING_KEY);
    }

    @Bean
    Binding newInventory(Queue newInventoryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(newInventoryQueue).to(exchange).with(NEW_INVENTORY_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper springInternalObjectMapper) {
        springInternalObjectMapper.findAndRegisterModules();
        return new Jackson2JsonMessageConverter(springInternalObjectMapper);
    }

    /*@Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(BREWING_QUEUE, NEW_INVENTORY_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }*/

}
