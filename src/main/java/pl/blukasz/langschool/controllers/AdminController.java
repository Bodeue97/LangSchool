package pl.blukasz.langschool.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.course.CourseService;
import pl.blukasz.langschool.users.User;
import pl.blukasz.langschool.users.UserRole;
import pl.blukasz.langschool.users.UserService;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;


    @GetMapping("/panel/add_course")
    public String addCourseView(Model model){
        List<User> teachers = userService.getAllUsersByRole("teacher");
        model.addAttribute("teachers", teachers);

        return "add_course_view";
    }

    @PostMapping("/panel/add_course")
    public RedirectView addCourse(HttpServletRequest request){
       String courseName = request.getParameter("name");
       String description = request.getParameter("description");
       Double price = Double.parseDouble(request.getParameter("price"));
       User teacher = userService.getUserByUsername(request.getParameter("teacherList"));

       Course course = new Course(courseName,description,price, teacher);
       courseService.addCourse(course);
       return new RedirectView("/panel/add_course");

    }


    @GetMapping("/courses_admin")
    public String adminCourseView(HttpServletRequest request, Model model){
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
         return "courses_admin";


    }


    @PostMapping("/courses_admin")
    public String deleteCourse(HttpServletRequest request){

        Course delete = courseService.getCourseByName(request.getParameter("delete"));
        courseService.deleteCourse(delete);


        return "course_delete_success";


    }

    @GetMapping("/courses_update")
    public String showUpdateCourseView(Model model, HttpServletRequest request){
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("teachers", userService.getAllUsersByRole("teacher"));

        return "courses_update";
    }

    @PostMapping("/courses_update")
    public String chooseUpdateCourse(HttpServletRequest request, Model model){
        Course course = courseService.getCourseByName(request.getParameter("edit"));
       model.addAttribute("course", course);
        model.addAttribute("teachers", userService.getAllUsersByRole("teacher"));

        return "courses_update_spec";


    }

    @GetMapping("/courses_update_spec")
    public String updateCourseView(Model model){


        return "courses_update_spec";
    }

    @PostMapping("/courses_update_spec")
    public String updateCourse(HttpServletRequest request){

        courseService.updateCourse(Long.valueOf(request.getParameter("id")), request.getParameter("courseName"), request.getParameter("description"), Double.parseDouble(request.getParameter("price")), userService.getUserByUsername(request.getParameter("teacherList") ));

        return "course_update_success";


    }

    @GetMapping("/edit_user_role")
    public String editUserRoleView(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);


        return "edit_role_view";
    }

    @PostMapping("/edit_user_role")
    public RedirectView editUserRole(HttpServletRequest request){

        if(request.getParameter("setTeacher") != null){
            userService.editUserRole(UserRole.TEACHER, userService.getUserByUsername(request.getParameter("setTeacher")));


        }
        if(request.getParameter("setStudent") != null){
            userService.editUserRole(UserRole.STUDENT, userService.getUserByUsername(request.getParameter("setStudent")));

        }
        if(request.getParameter("setClerk") != null){
            userService.editUserRole(UserRole.CLERK, userService.getUserByUsername(request.getParameter("setClerk")));
        }



        return  new RedirectView("/edit_user_role");

    }



}
