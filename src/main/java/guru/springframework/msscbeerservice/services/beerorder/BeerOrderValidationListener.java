package guru.springframework.msscbeerservice.services.beerorder;


import guru.springframework.msscbeerservice.config.RabbitMQConfig;
import guru.springframework.msscbeerservice.events.BeerOrderDto;
import guru.springframework.msscbeerservice.events.ValidateBeerOrderRequest;
import guru.springframework.msscbeerservice.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

    private final RabbitTemplate rabbitTemplate;
    private final BeerOrderValidator beerOrderValidator;


    @Transactional
    @RabbitListener(queues = RabbitMQConfig.VALIDATE_ORDER_QUEUE)
    public void listenBeerOrderToValidate(ValidateBeerOrderRequest validateBeerOrderRequest) {
        BeerOrderDto beerOrderDto = validateBeerOrderRequest.getBeerOrderDto();
        boolean isValid = beerOrderValidator.isBeerOrderValid(beerOrderDto);
        ValidateOrderResult message = ValidateOrderResult.builder().beerOrderId(beerOrderDto.getId()).isValid(isValid).build();

        rabbitTemplate.convertAndSend(RabbitMQConfig.VALIDATE_ORDER_RESULT_EXCHANGE, RabbitMQConfig.VALIDATE_ORDER_RESULT_ROUTING_KEY, message);
    }
}
