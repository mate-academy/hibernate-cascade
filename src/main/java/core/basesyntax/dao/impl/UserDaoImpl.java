package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.exceptions.DataProcessingException;
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
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot create user " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()){
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Cannot get user by id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()){
            return session.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cannot get any user", e);
        }
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction transaction = null;
        try  {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot remove user " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
