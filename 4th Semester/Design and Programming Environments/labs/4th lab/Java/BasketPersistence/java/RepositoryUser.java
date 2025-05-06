package ro.mpp2024;

import java.util.List;

public interface RepositoryUser extends Repository<Long, User>{
    List<User> findByUsernameAlphabetically(String username);
}
