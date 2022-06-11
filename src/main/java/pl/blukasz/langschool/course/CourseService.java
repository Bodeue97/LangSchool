package pl.blukasz.langschool.course;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public List<Course> getAllCourses(){
        List<Course> courses = courseRepository.findAll();
        return courses;

    }

}
