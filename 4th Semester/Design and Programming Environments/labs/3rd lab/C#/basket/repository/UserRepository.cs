namespace basket.repository;
using basket.domain;

interface UserRepository : Repository<long, User>{
    List<User> findByUsernameAlphabetically(String username);
}
