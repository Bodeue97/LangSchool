package pl.blukasz.langschool.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    @Override
    List<Course> findAll();

//    List<Course>findAllByCourseName();


}
