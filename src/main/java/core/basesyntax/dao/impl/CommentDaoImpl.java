package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            try {
                transaction = session.beginTransaction();
                session.save(entity);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException("can't add movie data: " + entity, e);
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Comment",Comment.class).list();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            try {
                transaction = session.beginTransaction();
                session.remove(entity);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new RuntimeException();
            }
        }
    }
}
