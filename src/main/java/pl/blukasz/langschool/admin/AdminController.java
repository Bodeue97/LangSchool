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


}
