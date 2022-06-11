package pl.blukasz.langschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "pl.blukasz")
public class LangSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(LangSchoolApplication.class, args);
    }

}
