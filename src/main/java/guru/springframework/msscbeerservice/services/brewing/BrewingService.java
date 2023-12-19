package guru.springframework.msscbeerservice.services.brewing;

import guru.springframework.msscbeerservice.config.RabbitMQConfig;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.events.BrewBeerEvent;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import guru.springframework.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final RabbitTemplate rabbitTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) //To run every 5 seconds.
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQuantityOnHand = beerInventoryService.getOnHandInventory(beer.getId());

            log.debug("Min Onhand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + invQuantityOnHand);

            if(beer.getMinOnHand() >= invQuantityOnHand) {
                rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "brewing.inventory",
                        new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
