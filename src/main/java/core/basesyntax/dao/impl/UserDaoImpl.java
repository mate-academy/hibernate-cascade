package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("nie udalo sie stworzyc usera", e);
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("nie udalo sie pobrac usera", e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            return session.createQuery("from User").list();
        } catch (Exception e) {
            throw new RuntimeException("nie udalo sie pobrac wszystkich userow", e);
        }
    }

    @Override
    public void remove(User entity) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("nie udalo sie usunac usera", e);
        }
    }
}
