package pl.blukasz.langschool.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.blukasz.langschool.users.User;
import pl.blukasz.langschool.users.UserService;
import pl.blukasz.langschool.users_course.UsersCourse;
import pl.blukasz.langschool.users_course.UsersCourseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ClerkController {

    @Autowired
    UsersCourseService usersCourseService;
    @Autowired
    UserService userService;

    @GetMapping("/manage_students")
    public String manageStudentsView(Model model, HttpServletRequest request){
        User clerk = userService.getUserByUsername(request.getRemoteUser());
        List<UsersCourse> usersCourses = usersCourseService.getAll();
        model.addAttribute("clerk", clerk);
        model.addAttribute("usersCourses",usersCourses);

        return "manage_students";
    }

    @PostMapping("/manage_students")
    public RedirectView manageStudents(HttpServletRequest request){

        usersCourseService.removeUsersCourse(Long.valueOf(request.getParameter("chosenUsersCourse")));

        return new RedirectView("/manage_students");
    }





}
