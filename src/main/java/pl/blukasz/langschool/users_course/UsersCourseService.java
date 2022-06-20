package pl.blukasz.langschool.users_course;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.course.CourseRepository;
import pl.blukasz.langschool.grade.Grade;
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


        }else{
            purchase.setStudent(student);
            purchase.setCourse(course);
            usersCourseRepository.save(purchase);
        }



    }


    public void setFinalGrade(Double finalGrade, UsersCourse uc){

        UsersCourse usersCourse = usersCourseRepository.findById(uc.getId()).get();
        usersCourse.setFinalGrade(finalGrade);
        usersCourseRepository.save(usersCourse);





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

    public List<UsersCourse> getAll(){
        return usersCourseRepository.findAll();
    }

    public UsersCourse getUsersCourseById(Long id){
        return usersCourseRepository.findById(id).get();
    }

    public void removeUsersCourse(Long id){
        usersCourseRepository.delete(usersCourseRepository.findById(id).get());
    }


}
