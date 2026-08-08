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
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can not save entity User by id: " + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entity;
    }

    @Override
    public User get(Long id) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            return session.get(User.class, id);
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can not find entity User by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can not find any Users", e);
        }
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can not remove entity User by id: " + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
