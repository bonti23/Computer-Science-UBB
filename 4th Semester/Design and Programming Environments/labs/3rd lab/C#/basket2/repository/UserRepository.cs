namespace basket2.repository;
using basket2.domain;

public interface UserRepository : Repository<long, User>{
    List<User> findByUsernameAlphabetically(String username);
}