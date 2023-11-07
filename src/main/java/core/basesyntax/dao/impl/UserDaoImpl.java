package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    @Override
    public User create(User entity) {
        return super.create(entity);
    }

    @Override
    public User get(Long id) {
        return super.get(id);
    }

    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    public void remove(User entity) {
        super.remove(entity);
    }
}
