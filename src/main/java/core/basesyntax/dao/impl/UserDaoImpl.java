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
    public User create(User user) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            return user;
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
            return session.createQuery("from User", User.class)
                    .getResultList();
        }
    }

    @Override
    public void remove(User user) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(session.merge(user));
            tx.commit();
        }
    }
}
