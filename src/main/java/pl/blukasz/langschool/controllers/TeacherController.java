package pl.blukasz.langschool.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
public class TeacherController {

    @Autowired
    GradeService gradeService;
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    UsersCourseService usersCourseService;





    @GetMapping("/add_grades")
    public String addGradesView(HttpServletRequest request, Model model){
        User teacher = userService.getUserByUsername(request.getRemoteUser());
        List<Course> myCourses = courseService.getAllByTeacher(teacher);
        List<UsersCourse> usersCourses = usersCourseService.getAllUsersCourseByCourses(myCourses);
        List<User> myStudents = usersCourses.stream().map(UsersCourse::getStudent).collect(Collectors.toList());
        model.addAttribute("students", myStudents);



        return "add_grades";


}


    @PostMapping("/add_grades")
    public RedirectView addGrades(HttpServletRequest request, RedirectAttributes ra){

       RedirectView rv =  new RedirectView("/add_grade_spec");
        ra.addAttribute("student",request.getParameter("add"));

        return rv;
    }

    @GetMapping("/add_grade_spec")
    public String addGradeSpecView(HttpServletRequest request, Model model, @RequestParam("student")String student){
        model.addAttribute("gradedStudent", userService.getUserByUsername(student));
        return "add_grade_spec";

    }


    @PostMapping("/add_grade_spec")
    public RedirectView addGradeSpec(HttpServletRequest request){
        User student = userService.getUserByUsername(request.getParameter("student"));
        List<Course> courses = courseService.getAllByTeacher(userService.getUserByUsername(request.getRemoteUser()));
        List<UsersCourse> usersCourses = usersCourseService.getUsersCourse(student);
        for (Course c:courses
             ) {
            for (UsersCourse uc: usersCourses
                 ) {
                if(c.getCourseName().equals(uc.getCourse().getCourseName())){
                    gradeService.addNewGrade(Double.parseDouble(request.getParameter("grade")), student, c);
                }
            }
        }
        return new RedirectView("/add_grades");

    }


    @GetMapping("/edit_grades")
    public String editGradesView(Model model, HttpServletRequest request){

        User teacher = userService.getUserByUsername(request.getRemoteUser());
        List<Course> myCourses = courseService.getAllByTeacher(teacher);
        List<UsersCourse> usersCourses = usersCourseService.getAllUsersCourseByCourses(myCourses);
        List<User> myStudents = usersCourses.stream().map(UsersCourse::getStudent).collect(Collectors.toList());
        model.addAttribute("students", myStudents);
        return "edit_grades";

    }

    @PostMapping("/edit_grades")
    public RedirectView editGrades(HttpServletRequest request, RedirectAttributes ra){

        RedirectView rv =  new RedirectView("/edit_grade_spec");
        ra.addAttribute("student",request.getParameter("edit"));

        return rv;
    }

    @GetMapping("/edit_grade_spec")
    public String editGradeSpecView(HttpServletRequest request, Model model, @RequestParam("student")String student){
        model.addAttribute("gradedStudent", userService.getUserByUsername(student));
        List<Grade> grades = gradeService.getAllStudentsGrades(userService.getUserByUsername(student));

        model.addAttribute("grades", grades);
        System.out.println(grades);
        return "edit_grade_spec";

    }
    @PostMapping("/edit_grade_spec")
    public RedirectView editGradeSpec(HttpServletRequest request, RedirectAttributes ra){

        RedirectView rv = new RedirectView("/edit_grade_this_one");
        ra.addAttribute("gradeId", request.getParameter("editGrade"));
        return rv;

    }

    @GetMapping("/edit_grade_this_one")
    public String thisOne(Model model, @RequestParam("gradeId")String gradeStr){

        Grade grade = gradeService.getGradeById(Long.parseLong(gradeStr));
        model.addAttribute("grade", grade);

        return "edit_grade_this_one";
    }

    @PostMapping("/edit_grade_this_one")
    public RedirectView thisOneDone(HttpServletRequest request){

        gradeService.editGrade(Double.valueOf(request.getParameter("newVal")), Long.parseLong(request.getParameter("id")));
        return new RedirectView("/panel");
    }





}
