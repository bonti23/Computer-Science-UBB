namespace basket.repository;
using basket.domain;

public interface GameRepository : Repository<long, Game> {
    List<Game> findByTypeOrderedByDate(String type);
}
