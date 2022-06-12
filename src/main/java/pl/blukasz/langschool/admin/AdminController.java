package pl.blukasz.langschool.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.course.CourseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    CourseService courseService;


    @GetMapping("/panel/add_course")
    public String addCourseView(){

        return "add_course_view";
    }

    @PostMapping("/panel/add_course")
    public String addCourse(HttpServletRequest request){
       String courseName = request.getParameter("name");
       String description = request.getParameter("description");
       Double price = Double.parseDouble(request.getParameter("price"));

       Course course = new Course(courseName,description,price);
       courseService.addCourse(course);
       return "add_course_view";

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

        return "courses_update";
    }

    @PostMapping("/courses_update")
    public String chooseUpdateCourse(HttpServletRequest request, Model model){
        Course course = courseService.getCourseByName(request.getParameter("edit"));
       model.addAttribute("course", course);
        return "courses_update_spec";


    }

    @GetMapping("/courses_update_spec")
    public String updateCourseView(){
        return "courses_update_spec";
    }

    @PostMapping("/courses_update_spec")
    public String updateCourse(HttpServletRequest request){


        courseService.updateCourse(Long.valueOf(request.getParameter("id")), request.getParameter("courseName"), request.getParameter("description"), Double.parseDouble(request.getParameter("price")));

        return "course_update_success";

    }




}
