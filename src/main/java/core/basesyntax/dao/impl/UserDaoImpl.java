package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
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
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.persist(entity);

        tx.commit();
        session.close();
        return entity;
    }

    @Override
    public User get(Long id) {
        Session session = factory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = factory.openSession();
        List<User> users = session
                .createQuery("FROM User", User.class)
                .getResultList();
        session.close();
        return users;
    }

    @Override
    public void remove(User user) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        User managed = session.get(User.class, user.getId());
        session.remove(managed);

        tx.commit();
        session.close();
    }
}
