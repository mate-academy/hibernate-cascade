package core.basesyntax.service;

import core.basesyntax.model.User;
import java.util.List;

public interface UserService {
    User create(User entity);
    
    User get(Long id);
    
    List<User> getAll();
    
    void remove(User entity);
}
