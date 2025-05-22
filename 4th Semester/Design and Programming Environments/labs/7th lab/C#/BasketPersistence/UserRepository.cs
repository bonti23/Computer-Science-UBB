namespace BasketPersistence;
using BasketModel;

public interface UserRepository : Repository<long, User>{
    User? FindOneByUsername(string username);
}