package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.User;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static core.basesyntax.HibernateUtil.getSessionFactory;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Can not create User: " + entity, e);
        }
    }

    @Override
    public User get(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can not get User by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = getSessionFactory().openSession()) {
            String sql = "FROM User";
            return session.createQuery(sql, User.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can not get all User", e);
        }
    }

    @Override
    public void remove(User entity) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            User managedUser = session.get(User.class, entity.getId());
            if (managedUser != null) {
                session.delete(managedUser);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Can not remove User: " + entity, e);
        }
    }
}
