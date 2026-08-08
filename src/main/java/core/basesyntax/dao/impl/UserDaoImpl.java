package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.persist(entity);

            transaction.commit();
            return entity;
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<User> users = session.createQuery("from User", User.class).list();
            transaction.commit();
            return users;
        }
    }

    @Override
    public void remove(User entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, entity.getId());
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        }
    }
}
