package pl.blukasz.langschool.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.course.CourseService;
import pl.blukasz.langschool.users.User;
import pl.blukasz.langschool.users.UserService;
import pl.blukasz.langschool.users_course.UsersCourse;
import pl.blukasz.langschool.users_course.UsersCourseService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StudentController {

    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    UsersCourseService usersCourseService;



    @GetMapping("/my_courses")
    public String myCoursesView(Model model, HttpServletRequest request){
        model.addAttribute("myCourses", usersCourseService.getUsersCourse(userService.getUserByUsername(request.getRemoteUser())));


        return "my_courses";

    }



    @PostMapping("/buy_course")
    public RedirectView buyCourseView(HttpServletRequest request){

        User buyer = userService.getUserByUsername(request.getRemoteUser());
        Course product = courseService.getCourseByName(request.getParameter("buy"));
        usersCourseService.buyCourse(buyer, product);


        return new RedirectView("/panel");

    }




}
