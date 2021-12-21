package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public User create(User entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't create user in DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        EntityManager entityManager = null;
        try {
            entityManager = factory.createEntityManager();
            return entityManager.find(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get user from DB by id: " + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<User> getAll() {
        EntityManager entityManager = null;
        try {
            entityManager = factory.createEntityManager();
            return entityManager.createQuery("SELECT a FROM User a", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users from DB", e);
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
            entityManager = factory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't remove user from DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
