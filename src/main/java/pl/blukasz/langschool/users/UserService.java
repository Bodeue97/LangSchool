package pl.blukasz.langschool.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder  passwordEncoder;


    public User getUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(()->new UsernameNotFoundException("Username "+username+" not found"));
        return user.get();


    }

    public User registerUser(User user)  {
        User registerUser = new User();
        registerUser.setFirstname(user.getFirstname());
        registerUser.setLastName(user.getLastName());
        registerUser.setUsername(user.getUsername());
        String password = passwordEncoder.encode(user.getPassword());
        registerUser.setPassword(password);
        registerUser.setActive(true);
        registerUser.setRole(UserRole.STUDENT);
            return userRepository.save(registerUser);
    }

    public List<User> getAllUsers(){
      return userRepository.findAll();
    }

    public void editUserRole(UserRole role, User user){
        user.setRole(role);
        userRepository.save(user);

    }


}
