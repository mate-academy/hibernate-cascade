package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
        } catch (Exception e) {
            throw new RuntimeException("Can not get Smile by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        Query<User> userQuery = factory
                .openSession().createQuery("from User", User.class);
        return userQuery.getResultList();
    }

    @Override
    public void remove(User entity) {
        factory.inTransaction(session -> session.remove(entity));
    }
}
