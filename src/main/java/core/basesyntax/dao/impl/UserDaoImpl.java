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
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            session.close();
            return entity;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Cannot add new User to DB", e);
        }
    }

    @Override
    public User get(Long id) {
        User user = null;
        try (Session session = factory.openSession()) {
            user = session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot get User from DB", e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            List<User> users = session.createQuery("from User").list();
            return users;
        } catch (Exception e) {
            throw new RuntimeException("Cannot get Users from DB", e);

        }
    }

    @Override
    public void remove(User entity) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Cannot remove User from DB", e);
        }
    }
}
