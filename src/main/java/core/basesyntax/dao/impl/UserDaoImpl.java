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
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't create comment ");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        Session session = null;
        Transaction tx = null;
        User entity = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            entity = (User) session.get(User.class, id);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't get comment ");
        }
        return entity;
    }

    @Override
    public List<User> getAll() {
        Session session = null;
        Transaction tx = null;
        List<User> entityList = null;
        String sql = "SELECT * FROM users";
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            entityList = session.createNativeQuery(sql, User.class).list();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't get comment ");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entityList;
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't remove comment");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
