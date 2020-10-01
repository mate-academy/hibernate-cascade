package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Log4j
public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User user) {
        log.info("Calling a create() method of UserDaoImpl class");
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.persist(user);
            log.info("Attempt to store user " + user + " in db.");
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Can't create user entity. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public User get(Long id) {
        log.info("Calling a get() method of UserDaoImpl class");
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            log.info("Attempt to retrieve user " + user + " from db.");
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Can't insert user entity. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAll() {
        log.info("Calling a getAll() method of UserDaoImpl class");
        try (Session session = factory.openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(User.class);
            criteriaQuery.from(User.class);
            log.info("Attempt to all users from db.");
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all users. ", e);
        }
    }

    @Override
    public void remove(User user) {
        log.info("Calling a remove() method of UserDaoImpl class");
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.delete(user);
            log.info("Attempt to delete user " + user + " from db.");
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Can't delete user entity. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
