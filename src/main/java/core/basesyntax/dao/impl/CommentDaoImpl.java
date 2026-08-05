package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    private static final String GET_ALL_COMMENTS_SQL = "FROM Comment";
    private Session session;
    private Transaction transaction;

    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        checkComment(entity);
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not add comment", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (id <= 0L) {
            throw new IllegalArgumentException("ID cannot be negative or zero");
        }
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Could not get comment", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Comment> getAll() {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            return session.createQuery(GET_ALL_COMMENTS_SQL, Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Could not get comments", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Comment entity) {
        checkComment(entity);
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not remove comment", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void checkComment(Comment entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }
        if (entity.getContent() == null || entity.getContent().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be null or empty");
        }
    }
}
