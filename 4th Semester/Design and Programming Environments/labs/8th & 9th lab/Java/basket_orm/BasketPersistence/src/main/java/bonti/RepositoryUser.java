package bonti;

import bonti.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositoryUser extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
