package pl.blukasz.langschool.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.users.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {

    Optional<Grade> findById(Long id);

    List<Grade> findAllByStudent(User student);

    List<Grade> findAllByStudentAndCourse(User student, Course course);

}
