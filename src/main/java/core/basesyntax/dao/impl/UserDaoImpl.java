package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import org.hibernate.SessionFactory;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        return executeInsideTransaction(session -> {
            if (entity.getComments() != null) {
                entity.getComments().forEach(comment -> comment.setUser(entity));
            }
            session.persist(entity);
            return entity;
        });
    }

    @Override
    public User get(Long id) {
        return executeInsideTransaction(session -> session.createQuery(
                        "SELECT u FROM User " +
                                "u LEFT JOIN FETCH u.comments WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .uniqueResult());
    }

    @Override
    public List<User> getAll() {
        return executeInsideTransaction(session ->
                session.createQuery("FROM User", User.class).list());
    }

    @Override
    public void remove(User entity) {
        executeInsideTransaction(session -> {
            User user = session.merge(entity);
            if (user.getComments() != null) {
                user.getComments().clear();
            }
            session.remove(user);
            return null;
        });
    }
}
