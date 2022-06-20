package pl.blukasz.langschool.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.course.CourseService;
import pl.blukasz.langschool.grade.Grade;
import pl.blukasz.langschool.grade.GradeService;
import pl.blukasz.langschool.users.User;
import pl.blukasz.langschool.users.UserService;
import pl.blukasz.langschool.users_course.UsersCourse;
import pl.blukasz.langschool.users_course.UsersCourseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StudentController {

    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    UsersCourseService usersCourseService;
    @Autowired
    GradeService gradeService;



    @GetMapping("/my_courses")
    public String myCoursesView(Model model, HttpServletRequest request){
        model.addAttribute("myCourses", usersCourseService.getUsersCourse(userService.getUserByUsername(request.getRemoteUser())));


        return "my_courses";

    }

    @PostMapping("/my_courses")
    public RedirectView myGrades(HttpServletRequest request, Model model){
        String username = request.getRemoteUser();
        String courseName = request.getParameter("thisCourse");
        RedirectView rv = new RedirectView("/my_grades");
        rv.getAttributesMap().put("student", username);
        rv.getAttributesMap().put("course", courseName);



        return rv;

    }
    @GetMapping("/my_grades")
    public String myGradesView(HttpServletRequest request, Model model, @RequestParam("student") String student, @RequestParam("course") String course){
        List<Grade> gradelist = gradeService.getAllGrades(userService.getUserByUsername(student), courseService.getCourseByName(course));
        List<String> grades = gradelist.stream().map(Grade::getGrade).map(Object::toString).collect(Collectors.toList());
        model.addAttribute("grades", grades);
        return "my_grades";
    }






    @PostMapping("/buy_course")
    public RedirectView buyCourseView(HttpServletRequest request){

        User buyer = userService.getUserByUsername(request.getRemoteUser());
        Course product = courseService.getCourseByName(request.getParameter("buy"));
        usersCourseService.buyCourse(buyer, product);


        return new RedirectView("/panel");

    }






}
