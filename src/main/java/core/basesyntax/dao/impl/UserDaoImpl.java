package core.basesyntax.dao.impl;

import static core.basesyntax.util.HqlQueries.GET_ALL_USERS;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        return executeInsideTransaction(session -> {
            session.persist(entity);
            return entity;
        });
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            User user = session.get(User.class, id);
            if (user != null) {
                Hibernate.initialize(user.getComments());
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Can't get user by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            List<User> users = session.createQuery(GET_ALL_USERS, User.class).list();
            for (User user : users) {
                Hibernate.initialize(user.getComments());
            }
            return users;
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users", e);
        }
    }

    @Override
    public void remove(User entity) {
        executeInsideTransaction(session -> {
            session.remove(entity);
        });
    }
}
