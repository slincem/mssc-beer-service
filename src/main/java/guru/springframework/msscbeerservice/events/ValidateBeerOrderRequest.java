package guru.springframework.msscbeerservice.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateBeerOrderRequest {
    private BeerOrderDto beerOrderDto;
}
