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
        Transaction transaction = null;
        Session session;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save entity to DB!", e);
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        Session session = factory.openSession();
        return session.get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM User a", User.class).getResultList();
        }
    }

    @Override
    public void remove(User entity) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        User entityWithoutComments = session.get(User.class, entity.getId());
        session.remove(entityWithoutComments);
        transaction.commit();
        session.close();
    }
}
