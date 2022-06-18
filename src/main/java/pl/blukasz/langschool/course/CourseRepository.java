package pl.blukasz.langschool.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.blukasz.langschool.users.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    @Override
    List<Course> findAll();

    Optional<Course> findCourseByCourseName(String courseName);

    Optional<Course> findCourseById(Long id);

    List<Course> findAllByTeacher(User teacher);


}
