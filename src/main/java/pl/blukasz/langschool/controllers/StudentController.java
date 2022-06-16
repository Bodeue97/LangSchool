package pl.blukasz.langschool.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.course.CourseService;
import pl.blukasz.langschool.users.UserService;

@Controller
public class StudentController {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;







}
