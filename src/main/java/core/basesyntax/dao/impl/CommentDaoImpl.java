package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    private static final String GET_ALL_QUERY = "SELECT * FROM comment";
    private static final String ADD_EXCEPTION_MESSAGE = "Failed to add comment to DB ";
    private static final String GET_EXCEPTION_MESSAGE = "Failed to get comment from DB by id=";
    private static final String REMOVE_EXCEPTION_MESSAGE = "Failed to remove comment from DB ";
    private static final String GET_ALL_EXCEPTION_MESSAGE = "Failed to get all comments from DB ";

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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(ADD_EXCEPTION_MESSAGE + entity, e);
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
            return Optional.ofNullable(session.get(Comment.class, id))
                        .orElseThrow(() -> new HibernateException(GET_EXCEPTION_MESSAGE + id));
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(GET_ALL_QUERY, Comment.class).getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException(GET_ALL_EXCEPTION_MESSAGE, e);
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(REMOVE_EXCEPTION_MESSAGE + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
