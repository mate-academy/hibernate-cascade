package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    private Session session;
    private Transaction transaction;

    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment comment) {
        checkComment(comment);
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t add Comment to database! " + comment);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve "
                    + "Comment from the database with id " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Comment", Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve comments from the database", e);
        }
    }

    @Override
    public void remove(Comment comment) {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant remove Comment: " + comment);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void checkComment(Comment comment) {
        if (comment == null) {
            throw new RuntimeException("Comment cannot be null! ");
        }
        if (comment.getContent() == null || comment.getContent().isEmpty()) {
            throw new RuntimeException("Comment content cannot be empty or null! " + comment);
        }
    }
}
