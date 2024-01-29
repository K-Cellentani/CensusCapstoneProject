package website.CensusController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"website.CensusController", "website.JBDC"})
public class HandlingFormSubmissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(HandlingFormSubmissionApplication.class, args);
    }



}



