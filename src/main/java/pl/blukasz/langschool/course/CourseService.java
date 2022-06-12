package pl.blukasz.langschool.course;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
