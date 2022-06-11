package pl.blukasz.langschool;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.course.CourseService;
import pl.blukasz.langschool.users.User;
import pl.blukasz.langschool.users.UserRepository;
import pl.blukasz.langschool.users.UserRole;
import pl.blukasz.langschool.users.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @GetMapping
    public String homeView(HttpServletRequest request){
        if(request.getRemoteUser() != null)
        return "index_logged";
        return "index";
    }

    @GetMapping("/login")
    public String loginView(){
        return "login";
    }



    @GetMapping("/panel")
    public String panelView(HttpServletRequest request){
        User user = userService.getUserByUsername(request.getUserPrincipal().getName());
        if(user.getRole().equals(UserRole.ADMIN)){
            return "panel_admin";
        }
        if(user.getRole().equals(UserRole.TEACHER)){
            return "panel_teacher";
        }
        if(user.getRole().equals(UserRole.CLERK)){
            return "panel_clerk";
        }
        else
            return "panel_student";


    }

    @GetMapping("/register")
    public String registerView(){
        return "registration_form";

    }

    @PostMapping("/register")
    public String register(HttpServletRequest request){
        User user = new User();
        user.setName(request.getParameter("firstname"));
        user.setLastName(request.getParameter("lastname"));
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        userService.registerUser(user);
        return "login";


    }

    @GetMapping("/courses")
    public String coursesView(Model model){
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses.jsp";

    }

}
