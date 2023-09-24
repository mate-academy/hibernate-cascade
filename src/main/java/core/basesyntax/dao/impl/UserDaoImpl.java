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
        try {
            return factory.fromTransaction(session -> {
                session.persist(entity);
                return entity;
            });
        } catch (Exception e) {
            throw new RuntimeException("Can't create User: " + entity, e);
        }
    }

    @Override
    public User get(Long id) {
        try {
            return factory.fromSession(session -> session.get(User.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can't get User by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return factory.fromSession(session -> session.createQuery("FROM User", User.class)
                    .getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Can't get List of User from db", e);
        }
    }

    @Override
    public void remove(User entity) {
        try {
            factory.inTransaction(session -> session.remove(entity));
        } catch (Exception e) {
            throw new RuntimeException("Can't remove User: " + entity, e);
        }
    }
}
