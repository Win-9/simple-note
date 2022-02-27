package simple.simplenote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoprojectApplication {

    @RequestMapping("/")
    public String home(){

        return "Running Back-End Server!";
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoprojectApplication.class, args);
    }

}
