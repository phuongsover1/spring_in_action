package tacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.blockhound.BlockHound;

@Slf4j
@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        BlockHound.install();
        SpringApplication.run(TacoCloudApplication.class, args);
    }


    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:8080");
    }
}



