package pl.blukasz.langschool.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.course.CourseRepository;
import pl.blukasz.langschool.users.User;
import pl.blukasz.langschool.users.UserRepository;
import pl.blukasz.langschool.users.UserRole;
import pl.blukasz.langschool.users_course.UsersCourse;
import pl.blukasz.langschool.users_course.UsersCourseRepository;

@Configuration
public class StartupEntitiesConfiguration {

    private final PasswordEncoder passwordEncoder;

    public StartupEntitiesConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, CourseRepository courseRepository, UsersCourseRepository usersCourseRepository){
        String passwordAdmin = passwordEncoder.encode("a");
        String passwordTeacher = passwordEncoder.encode("t");
        String passwordStudent = passwordEncoder.encode("s");
        String passwordClerk = passwordEncoder.encode("c");
        return args ->{
            User admin = new User("admin", "admin", "a", passwordAdmin, UserRole.ADMIN, true);
            User teacher = new User("teacher", "teacher", "t", passwordTeacher, UserRole.TEACHER, true);
            User student = new User("student", "student", "s", passwordStudent, UserRole.STUDENT, true);
            User clerk = new User("clerk", "clerk", "c", passwordClerk, UserRole.CLERK, true);
            Course course = new Course("c", "c", 1.0, teacher);
            UsersCourse usersCourse = new UsersCourse(student, course);

            userRepository.save(admin);
            userRepository.save(student);
            userRepository.save(teacher);
            userRepository.save(clerk);
            courseRepository.save(course);
            usersCourseRepository.save(usersCourse);
        };


    }
}