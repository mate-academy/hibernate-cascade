package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User user) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = factory.openSession();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to save a user: " + user, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return user;
    }

    @Override
    public User get(Long id) {
        try (EntityManager entityManager = factory.openSession()) {
            return entityManager.find(User.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get a user with id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (EntityManager entityManager = factory.openSession()) {
            Query query = entityManager.createQuery("from User", User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get all users", e);
        }
    }

    @Override
    public void remove(User user) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = factory.openSession();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to remove a user: " + user, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
