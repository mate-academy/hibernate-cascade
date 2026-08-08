package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    private static final String CANNOT_CREATE_COMMENT_TEMPLATE = "Cannot create comment: ";
    private static final String CANNOT_GET_COMMENT_TEMPLATE = "Cannot get comment with id: ";
    private static final String CANNOT_GET_COMMENTS = "Cannot get comments";
    private static final String CANNOT_REMOVE_COMMENT_TEMPLATE = "Cannot remove comment: ";
    private static final String GET_ALL_COMMENTS_QUERY = "FROM Comment";

    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(CANNOT_CREATE_COMMENT_TEMPLATE + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException(CANNOT_GET_COMMENT_TEMPLATE + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(GET_ALL_COMMENTS_QUERY, Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(CANNOT_GET_COMMENTS, e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(CANNOT_REMOVE_COMMENT_TEMPLATE + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
