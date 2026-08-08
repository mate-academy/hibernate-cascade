package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.Message;
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
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not add a entity to DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            User foundedEntity = session.find(User.class, id);
            Hibernate.initialize(foundedEntity.getComments());
            transaction.commit();
            return foundedEntity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not get a " + User.class.getSimpleName()
                    + " to a DB. ", e);
        }
    }

    @Override
    public List<User> getAll() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            List<User> allFoundedEntities =
                    session.createQuery("FROM " + User.class.getName(), User.class)
                            .getResultList();

            transaction.commit();
            return allFoundedEntities;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can not get a " + Message.class.getSimpleName()
                    + " to a DB. ", e);

        }
    }

    @Override
    public void remove(User entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not delete " + User.class.getSimpleName()
                    + " from a DB.", e);
        }
    }
}
