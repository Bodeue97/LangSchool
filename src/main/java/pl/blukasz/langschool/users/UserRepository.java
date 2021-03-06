package pl.blukasz.langschool.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.blukasz.langschool.users_course.UsersCourse;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    List<User> findAllByRole(UserRole role);





}
