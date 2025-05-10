package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public User create(User entity) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void remove(User entity) {

    }
}
