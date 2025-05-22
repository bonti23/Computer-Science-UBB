package bonti.repository;

import bonti.domain.Chemist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChemistRepository extends JpaRepository<Chemist, Long> {
    Chemist findByUsernameAndPassword(String username, String password);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Chemist findByUsername(String username);
}
