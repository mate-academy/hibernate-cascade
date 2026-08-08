package core.basesyntax.dao;

import core.basesyntax.model.User;
import java.util.List;

public interface UserDao extends GenericDao<User> {
    User create(User entity);

    User get(Long id);

    List<User> getAll();

    void remove(User entity);
}
