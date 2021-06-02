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
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create comment: " + entity, exception);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception exception) {
            throw new RuntimeException("Can't get comment by id: " + id, exception);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Comment", Comment.class).getResultList();
        } catch (Exception exception) {
            throw new RuntimeException("Can't get comments table from DB", exception);
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove comment: " + entity, exception);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
