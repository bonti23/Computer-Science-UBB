namespace BasketPersistence;
using BasketModel;

public interface GameRepository : Repository<long, Game> {
    List<Game> findByTypeOrderedByDate(String type);
}
