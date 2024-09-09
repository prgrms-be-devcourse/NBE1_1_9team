package gc.cafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeApplication.class, args);
    }

}
