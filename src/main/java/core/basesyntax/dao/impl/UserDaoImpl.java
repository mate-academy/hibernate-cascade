package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User user) {
        try (var session = factory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
            return user;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to save User entity: " + user, ex);
        }
    }

    @Override
    public User get(Long id) {
        try (var session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve User entity by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        final String query = "FROM User";
        try (var session = factory.openSession()) {
            return session.createQuery(query, User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all User entities", e);
        }
    }

    @Override
    public void remove(User user) {
        try (var session = factory.openSession()) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove User entity: " + user, e);
        }
    }
}
