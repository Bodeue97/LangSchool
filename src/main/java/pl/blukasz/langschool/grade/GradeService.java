package pl.blukasz.langschool.grade;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.users.User;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class GradeService {

    @Autowired
    GradeRepository gradeRepository;

    public Grade getGradeById(Long id){
        return gradeRepository.findById(id).get();
    }

    public List<Grade> getAllGrades(User student, Course course){
        return gradeRepository.findAllByStudentAndCourse(student, course);

    }
    public List<Grade> getAllStudentsGrades(User student){
        return gradeRepository.findAllByStudent(student);
    }

    public void addNewGrade(Double grade, User student, Course course){
        System.out.println(grade + " addNewGrade");
        gradeRepository.save(new Grade(grade, student, course));
    }


    public void editGrade(Double edit, Long id) throws EntityNotFoundException{
        Grade grade = gradeRepository.findById(id).orElseThrow(()->new EntityNotFoundException("grade not found"));
        System.out.println(grade.getGrade());
        grade.setGrade(edit);
        System.out.println(grade.getGrade());
        gradeRepository.save(grade);



    }

    public Double getAverage(User student, Course course){
        List<Grade> grades = getAllGrades(student,course);
        return grades.stream().mapToDouble(Grade::getGrade).sum() / grades.size();
    }




}
