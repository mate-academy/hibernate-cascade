package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        factory.inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get a User with id : " + id);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can get all from User", e);
        }
    }

    @Override
    public void remove(User entity) {
        factory.inTransaction(session -> session.remove(entity));
    }
}
