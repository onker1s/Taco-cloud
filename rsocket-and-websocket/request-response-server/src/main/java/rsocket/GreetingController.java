package rsocket;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Controller
@Slf4j
public class GreetingController {
    @MessageMapping("greeting")
    public Mono<String> handleGreeting(Mono<String> greetingMono) {
        return greetingMono
                .doOnNext(greeting ->
                        log.info("Received a greeting: {}", greeting))
                .map(greeting -> "Hello back to you!");
    }
}
