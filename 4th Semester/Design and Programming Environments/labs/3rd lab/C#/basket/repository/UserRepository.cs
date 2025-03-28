namespace basket.repository;
using basket.domain;

public interface UserRepository : Repository<long, User>{
    List<User> findByUsernameAlphabetically(String username);
}
