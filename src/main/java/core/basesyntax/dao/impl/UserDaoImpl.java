package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get User from DB with id " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            Query<User> getAllUsers = session.createQuery("SELECT u FROM User u", User.class);
            return getAllUsers.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all users from DB");
        }
    }
}
