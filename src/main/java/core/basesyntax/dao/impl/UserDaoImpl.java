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
    public User create(User entity) {
        factory.inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public User get(Long id) {
        return factory.fromSession(
                session -> session.createQuery("from User u"
                                + " left join fetch u.comments"
                                + " where u.id = :id", User.class)
                        .setParameter("id", id)
                        .getSingleResult());
    }

    @Override
    public List<User> getAll() {
        return factory.fromSession(
                session -> session.createQuery("from User u"
                                + " left join fetch u.comments", User.class)
                        .getResultList());
    }

    @Override
    public void remove(User entity) {
        factory.inTransaction(session -> session.remove(entity));
    }
}
