package core.basesyntax.dao;

import core.basesyntax.model.User;
import java.util.List;

public interface UserDao extends GenericDao<User> {
    @Override
    default User create(User entity) {
        return null;
    }

    @Override
    default User get(Long id) {
        return null;
    }

    @Override
    default List<User> getAll() {
        return null;
    }

    @Override
    default void remove(User entity) {

    }
}
