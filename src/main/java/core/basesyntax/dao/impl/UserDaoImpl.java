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
    public User create(User entity) {
        return persistEntity(entity);
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            User user = session.find(User.class, id);
            if (user != null) {
                Hibernate.initialize(user.getComments());
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Can't get user by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        return getAllEntity(User.class);
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            User managedUser = session.find(User.class, entity.getId());
            if (managedUser != null) {
                session.remove(managedUser);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove entity " + entity + " from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
