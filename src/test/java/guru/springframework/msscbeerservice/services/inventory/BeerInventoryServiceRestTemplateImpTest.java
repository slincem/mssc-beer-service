package guru.springframework.msscbeerservice.services.inventory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@Disabled // utility for manual testing
@SpringBootTest
class BeerInventoryServiceRestTemplateImpTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @Test
    void getOnHandInventory() {
        UUID beerUuid = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
        Integer quantityOnHand = beerInventoryService.getOnHandInventory(beerUuid);

        System.out.println(quantityOnHand);
    }

}