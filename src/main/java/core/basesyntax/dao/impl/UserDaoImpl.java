package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    @Override
    public User create(User entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Save related entities first, if any
            if (entity.getComments() != null) {
                for (Comment comment : entity.getComments()) {
                    session.save(comment);
                }
            }

            // Save the main entity
            session.save(entity);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error occurred while saving entity", e);
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        return super.get(id);
    }

    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    public void remove(User entity) {
        super.remove(entity);
    }
}
