package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User user) {
        return super.create(user);
    }

    @Override
    public User get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            return query.getResultList();
        }
    }

    @Override
    public void remove(User user) {
        super.remove(user);
    }
}
