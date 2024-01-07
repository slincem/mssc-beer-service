package guru.springframework.msscbeerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
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

    // Esta es creada en Order-Service, por lo que solo necesito su nombre.
    public static final String VALIDATE_ORDER_QUEUE = "validate-order-queue";

    public static final String VALIDATE_ORDER_RESULT_EXCHANGE = "validate-order-result-exchange";
    public static final String VALIDATE_ORDER_RESULT_QUEUE = "validate-order-result-queue";
    public static final String VALIDATE_ORDER_RESULT_ROUTING_KEY = "validate-order-result";

    /*
     * TOPIC EXCHANGE, WORKS FOR DIFFERENT ROUTING KEY DEFINITIONS: (BREWING - NEW INVENTORY    IN THIS CASE).
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    /*
     * BREWING QUEUE CONFIG
     */
    @Bean
    Queue brewingQueue() {
        return new Queue(BREWING_QUEUE, false);
    }

    @Bean
    Binding bindingBrewing(Queue brewingQueue, TopicExchange exchange) {
        return BindingBuilder.bind(brewingQueue).to(exchange).with(BREWING_ROUTING_KEY);
    }


    /*
     * NEW INVENTORY QUEUE CONFIG
     */
    @Bean
    Queue newInventoryQueue() {
        return new Queue(NEW_INVENTORY_QUEUE, false);
    }

    @Bean
    Binding bindingNewInventory(Queue newInventoryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(newInventoryQueue).to(exchange).with(NEW_INVENTORY_ROUTING_KEY);
    }


    /*
     * VALIDATE ORDER QUEUE CONFIG, WITH ITS OWN DIRECT EXCHANGE.
     */
    @Bean
    DirectExchange validateOrderResultExchange() {
        return new DirectExchange(VALIDATE_ORDER_RESULT_EXCHANGE);
    }

    @Bean
    Queue validateOrderResultQueue() {
        return new Queue(VALIDATE_ORDER_RESULT_QUEUE, false);
    }

    @Bean
    Binding validateOrderResultBinding(Queue validateOrderResultQueue, DirectExchange validateOrderResultExchange) {
        return BindingBuilder.bind(validateOrderResultQueue).to(validateOrderResultExchange).with(VALIDATE_ORDER_RESULT_ROUTING_KEY);
    }


    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper springInternalObjectMapper) {
        springInternalObjectMapper.findAndRegisterModules();
        return new Jackson2JsonMessageConverter(springInternalObjectMapper);
    }

}
