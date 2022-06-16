package pl.blukasz.langschool.course;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.blukasz.langschool.users.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public List<Course> getAllCourses(){
        return courseRepository.findAll();


    }


    public Course getCourseByName(String courseName) throws EntityNotFoundException {

        Optional<Course> course = courseRepository.findCourseByCourseName(courseName);
        course.orElseThrow(()->new EntityNotFoundException("Course with name " + courseName + " not found"));
        return course.get();




    }

    public Course addCourse(Course course){
        return courseRepository.save(course);

    }

    public void deleteCourse(Course course){
        courseRepository.delete(course);


    }

    @Transactional
    public void updateCourse(Long id, String courseName, String description, Double price, User teacher) throws EntityNotFoundException{

        Course course = courseRepository.findCourseById(id).orElseThrow(()->new EntityNotFoundException("Course with id " + id + " not found"));
        course.setCourseName(courseName);
        course.setDescription(description);
        course.setPrice(price);
        course.setTeacher(teacher);


    }


}
