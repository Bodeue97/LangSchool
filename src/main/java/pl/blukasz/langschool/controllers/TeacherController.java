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
        List<User> students = getTeachersStudents(request.getRemoteUser());
        model.addAttribute("students", students);



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
       List<User> students =  getTeachersStudents(request.getRemoteUser());
        model.addAttribute("students", students);


        return "edit_grades";

    }
    @PostMapping("/edit_grades")
    public String editGrades(HttpServletRequest request, Model model){
        User student = userService.getUserByUsername(request.getParameter("edit"));
        List<Grade> studentsGrades = gradeService.getAllStudentsGrades(student);
        model.addAttribute("grades", studentsGrades);
        return "edit_students_grades";


    }
    @PostMapping("/edit_students_grades")
    public String editStudentsGrades(HttpServletRequest request, Model model){

        Grade editThisGrade = gradeService.getGradeById(Long.valueOf(request.getParameter("editThis")));
        model.addAttribute("editThisGrade",editThisGrade);

        return "edit_grade_spec";
    }

    @PostMapping("/edit_grade_spec")
    public RedirectView editThisGrade(HttpServletRequest request){

        Double newVal = Double.valueOf(request.getParameter("newVal"));
        Long id = Long.valueOf(request.getParameter("id"));
        gradeService.editGrade(newVal, id);

        return new RedirectView("/panel");


    }



    @GetMapping("/set_final_grades")
    public String setFinalGradesView(){
        return "grades_set";
    }

    @PostMapping("/set_grades")
    public String setFinalGrades(HttpServletRequest request){

        User teacher = userService.getUserByUsername(request.getRemoteUser());
        List<Course> courses = courseService.getAllByTeacher(teacher);
        List<User> students = getTeachersStudents(teacher.getUsername());
        for (User s: students
             ) {
            List<UsersCourse> usersCourses = usersCourseService.getUsersCourse(s);
            for (UsersCourse uc:usersCourses
                 ) {

                Double avg = gradeService.getAverage(s, uc.getCourse());
                usersCourseService.setFinalGrade(avg, uc);
            }
        }



        return "grades_are_set";
    }











    public List<User> getTeachersStudents(String teacherName){
        User teacher = userService.getUserByUsername(teacherName);
        List<Course> teachersCourses = courseService.getAllByTeacher(teacher);
        List<UsersCourse> teachersCoursesStudents = usersCourseService.getAllUsersCourseByCourses(teachersCourses);
        return    teachersCoursesStudents.stream().map(UsersCourse::getStudent).collect(Collectors.toList());

    }
}
