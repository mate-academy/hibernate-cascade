package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import java.util.Optional;
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
        Optional<User> user = super.get(id, User.class);
        return user.orElse(null);
    }

    @Override
    public List<User> getAll() {
        return super.getAll(User.class);
    }

    @Override
    public void remove(User entity) {
        super.removeEntity(entity);
    }
}
