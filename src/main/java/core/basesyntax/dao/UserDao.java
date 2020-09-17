package core.basesyntax.dao;

import core.basesyntax.model.User;

public interface UserDao extends GenericDao<User> {
    void create(User user);
}
