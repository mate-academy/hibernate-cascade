package core.basesyntax.dao;

import core.basesyntax.model.User;
import java.util.List;

public interface UserDao extends GenericDao<User> {
    @Override
    User create(User entity);

    @Override
    User get(Long id);

    @Override
    List<User> getAll();

    @Override
    void remove(User entity);
}
