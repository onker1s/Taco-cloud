package rsocket;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;

@Configuration
@Slf4j
public class RSocketClientConfiguration {

    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requesterBuilder) {
        return args -> {
            RSocketRequester requester = requesterBuilder.websocket(
                    URI.create("ws://localhost:8080/rsocket"));

            Flux<GratuityIn> gratuityInFlux =
                    Flux.fromArray(new GratuityIn[] {
                                    new GratuityIn(BigDecimal.valueOf(35.50), 18),
                                    new GratuityIn(BigDecimal.valueOf(10.00), 15),
                                    new GratuityIn(BigDecimal.valueOf(23.25), 20),
                                    new GratuityIn(BigDecimal.valueOf(52.75), 18),
                                    new GratuityIn(BigDecimal.valueOf(80.00), 15)
                            })
                            .delayElements(Duration.ofSeconds(1));

            requester
                    .route("gratuity")
                    .data(gratuityInFlux)
                    .retrieveFlux(GratuityOut.class)
                    .subscribe(out ->
                            log.info(out.getPercent() + "% gratuity on "
                                    + out.getBillTotal() + " is "
                                    + out.getGratuity()));
        };
    }


}
