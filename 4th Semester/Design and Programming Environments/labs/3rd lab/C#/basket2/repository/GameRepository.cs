namespace basket2.repository;
using basket2.domain;

public interface GameRepository : Repository<long, Game> {
    List<Game> findByTypeOrderedByDate(String type);
}