package gradproject.demo.repository;

import gradproject.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);
    User findUserByIdentityNumber(Long identityNumber);
   // User save(User user);
}