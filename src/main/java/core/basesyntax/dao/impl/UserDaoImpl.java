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
        return executeInsideTransaction(session -> {
            session.save(entity);
            return entity;
        });
    }

    @Override
    public User get(Long id) {
        return executeInsideTransaction(session -> session.get(User.class, id));
    }

    @Override
    public List<User> getAll() {
        return executeInsideTransaction(session -> session.createQuery("FROM User", User.class).list());
    }

    @Override
    public void remove(User entity) {
        executeInsideTransaction(session -> {
            session.remove(entity);
            return null;
        });
    }
}