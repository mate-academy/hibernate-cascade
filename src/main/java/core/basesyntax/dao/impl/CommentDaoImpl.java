package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Comment;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public static final String ERROR_DURING_CREATION_COMMENT =
            "Error during creation comment -> %s";
    public static final String NO_COMMENT_WITH_SUCH_ID =
            "No Comment with such id -> %d";
    public static final String ERROR_DURING_RETRIEVING_COMMENT_WITH_ID =
            "Error during retrieving comment with id -> %s";
    public static final String ERROR_DURING_RETRIEVING_ALL_COMMENTS_FROM_DB =
            "Error during retrieving all comments from db.";
    public static final String SELECT_ALL_COMMENTS = "SELECT c FROM Comment c";

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
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    ERROR_DURING_CREATION_COMMENT.formatted(entity));
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            Optional<Comment> commentFromDb = Optional.ofNullable(session.get(Comment.class, id));
            return commentFromDb.orElseThrow(() -> new EntityNotFoundException(
                    NO_COMMENT_WITH_SUCH_ID.formatted(id)));
        } catch (Exception e) {
            throw new DataProcessingException(
                    ERROR_DURING_RETRIEVING_COMMENT_WITH_ID.formatted(id), e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            SELECT_ALL_COMMENTS, Comment.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(ERROR_DURING_RETRIEVING_ALL_COMMENTS_FROM_DB, e);
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
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
