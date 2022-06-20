package pl.blukasz.langschool.users_course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.users.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersCourseRepository extends JpaRepository<UsersCourse, Long> {

   Optional<UsersCourse> findById(Long id);

   Optional<List<UsersCourse>> getAllUsersCourseByStudent(User student);

   UsersCourse findByStudentAndCourse(User student, Course course);

   List<UsersCourse> findAllByCourse(Course course);

   List<UsersCourse> findAllByCourseIn(List<Course> courseList);

   List<UsersCourse> findAll();

}
