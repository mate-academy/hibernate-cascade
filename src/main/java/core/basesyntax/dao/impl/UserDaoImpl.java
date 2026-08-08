package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        return createObj(entity);
    }

    @Override
    public User get(Long id) {
        return getObj(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return getAllObj(User.class);
    }

    @Override
    public void remove(User entity) {
        removeObj(entity);
    }
}
