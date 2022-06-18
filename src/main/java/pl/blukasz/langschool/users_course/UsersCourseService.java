package pl.blukasz.langschool.users_course;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.users.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UsersCourseService {

    @Autowired
    UsersCourseRepository usersCourseRepository;


    public void buyCourse( User student, Course course){
        UsersCourse purchase = new UsersCourse(student, course);
        if(usersCourseRepository.findByStudentAndCourse(student, course) != null){
            System.out.println("You already have this course");

        }else{
            usersCourseRepository.save(purchase);
        }



    }


    public void setFinalGrade(Double finalGrade, UsersCourse usersCourse){
        usersCourseRepository.findByStudentAndCourse(usersCourse.getStudent(), usersCourse.getCourse()).setFinalGrade(finalGrade);




    }

    public List<UsersCourse> getUsersCourse(User student) throws EntityNotFoundException {
        Optional<List<UsersCourse>> usersCourses = usersCourseRepository.getAllUsersCourseByStudent(student);
        usersCourses.orElseThrow(()-> new EntityNotFoundException("User has no courses"));

       return usersCourses.get();

    }

    public List<UsersCourse> getAllUsersCourseByCourse(Course course){

        return usersCourseRepository.findAllByCourse(course);

    }

    public List<UsersCourse> getAllUsersCourseByCourses(List<Course> courseList){
        return usersCourseRepository.findAllByCourseIn(courseList);

    }



}
