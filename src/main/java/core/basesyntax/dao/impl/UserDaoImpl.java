package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao implements UserDao {

    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User get(Long id) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            Hibernate.initialize(user.getComments());
            transaction.commit();
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to find User by ID", e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
            return users;
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all Users", e);
        }
    }

    @Override
    public User create(User user) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save User", e);
        }
        return user;
    }

    @Override
    public void remove(User entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove from DB user = " + entity.toString(), e);
        }
    }

    private Session getSession() {
        return factory.openSession();
    }
}
