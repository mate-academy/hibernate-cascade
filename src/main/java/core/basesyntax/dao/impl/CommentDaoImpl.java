package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Comment;
import java.util.List;
import java.util.function.Consumer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        executeInTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Comment not found with id: " + id);
        }
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments;
        try (Session session = factory.openSession()) {
            comments = session.createQuery("from Comment").list();
            return comments;
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Comments was not found. " + e.getMessage());
        }
    }

    @Override
    public void remove(Comment entity) {
        executeInTransaction(session -> session.remove(entity));
    }

    private void executeInTransaction(Consumer<Session> action) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            action.accept(session);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Operation with comment not done. " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
