package pl.blukasz.langschool.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.blukasz.langschool.users.User;
import pl.blukasz.langschool.users.UserRepository;
import pl.blukasz.langschool.users.UserRole;

@Configuration
public class AdminConfiguration {

    private final PasswordEncoder passwordEncoder;

    public AdminConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        String password = passwordEncoder.encode("admin");
        return args ->{
            User admin = new User("admin", "admin", "admin", password, UserRole.ADMIN, true);

            userRepository.save(admin);
        };


    }
}