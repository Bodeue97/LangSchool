package pl.blukasz.langschool.users_course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersCourseRepository extends JpaRepository<UsersCourse, Long> {




}
