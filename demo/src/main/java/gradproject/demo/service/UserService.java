package gradproject.demo.service;

import gradproject.demo.entity.User;
import gradproject.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        log.info("Users has been queried. Found:" + allUsers.size());
        return allUsers;
    }
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public User findUserByIdentityNumber(Long identityNumber) {
        return userRepository.findUserByIdentityNumber(identityNumber);
    }

    public User saveUser(User user) {
        log.info("User with identity number " + user.getIdentityNumber() + " will saved." );
        return userRepository.save(user);
    }

    public void deleteUserByIdentity(Long identityNumber) {
        User user = userRepository.findUserByIdentityNumber(identityNumber);
        userRepository.delete(user);
        log.info("User with identity number " + user.getIdentityNumber() + " deleted." );
    }

}
