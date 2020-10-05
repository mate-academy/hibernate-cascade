package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User user) {
        return super.create(user);
    }

    @Override
    public User get(Long id) {
        return super.get(id, User.class);
    }

    @Override
    public List<User> getAll() {
        return super.getALl(User.class);
    }

    @Override
    public void remove(User user) {
        super.remove(user);
    }
}
