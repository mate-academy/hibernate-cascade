package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();

            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't create new User " + entity, e);
        }
    }

    @Override
    public User get(Long id) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            User user = session
                    .createQuery("SELECT user "
                                    + "FROM User user "
                                    + "LEFT JOIN FETCH user.comments "
                                    + "WHERE user.id = :id",
                            User.class)
                    .setParameter("id", id)
                    .getSingleResult();

            transaction.commit();

            return user;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't find User by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            List<User> users = session
                    .createQuery("SELECT DISTINCT user "
                                    + "FROM User user "
                                    + "LEFT JOIN FETCH user.comments",
                            User.class)
                    .getResultList();
            transaction.commit();

            return users;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't get all Users ", e);
        }
    }

    @Override
    public void remove(User entity) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            User user = session.merge(entity);
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove User from DB " + entity, e);
        }
    }
}
