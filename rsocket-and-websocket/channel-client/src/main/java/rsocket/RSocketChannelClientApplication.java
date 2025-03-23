package rsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RSocketChannelClientApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RSocketChannelClientApplication.class, args);


        Thread.sleep(10000);
    }

}