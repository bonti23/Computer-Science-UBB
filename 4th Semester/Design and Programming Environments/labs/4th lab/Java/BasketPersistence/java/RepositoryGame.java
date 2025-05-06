package ro.mpp2024;

import java.util.List;

public interface RepositoryGame extends Repository<Long, Game> {
    List<Game> findByTypeOrderedByDate(String type);
}
