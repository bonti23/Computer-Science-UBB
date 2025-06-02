package bonti;

import bonti.model.Game;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositoryGame extends JpaRepository<Game, Long> {
    List<Game> findByTypeOrderByDateAsc(String type);
}
