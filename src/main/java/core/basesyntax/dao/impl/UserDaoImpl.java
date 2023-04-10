package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
            return entity;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't add user to DB" + entity);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            return entityManager.find(User.class, id);
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't get user from DB by id" + id);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<User> getAll() {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            final Query<User> getAllComments = (Query<User>) entityManager.createQuery(
                    "from User", User.class);
            return getAllComments.getResultList();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't get all users from DB" + e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public void remove(User entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't remove user from DB " + entity);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
