package guru.springframework.msscbeerservice.events;


import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 5412469834960308178L;

    private BeerDto beerDto;

}
