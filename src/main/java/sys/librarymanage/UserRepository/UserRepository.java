package sys.librarymanage.UserRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sys.librarymanage.Entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> getUsersByUsername(String username);

    User getUserByUsername(String username);

    boolean existsUserByUsername(String username);
}
