package pl.blukasz.langschool.users_course;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.users.User;

@Service
public class UsersCourseService {

    @Autowired
    UsersCourseRepository usersCourseRepository;


    public UsersCourse buyCourse( User student, Course course){
        UsersCourse purchase = new UsersCourse(student, course);

        return usersCourseRepository.save(purchase);


    }



}
