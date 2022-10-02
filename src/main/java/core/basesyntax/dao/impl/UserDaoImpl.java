package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can not get user from DB with id " + id);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can not get all users from DB");
        }
    }

}
