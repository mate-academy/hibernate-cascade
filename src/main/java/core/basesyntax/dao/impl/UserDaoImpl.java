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
        return (User) super.createEntity(entity);
    }

    @Override
    public User get(Long id) {
        return (User) super.getEntity(id, User.class);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) super.getAllEntities(User.class);
    }

    @Override
    public void remove(User entity) {
        super.removeEntity(entity);
    }
}
