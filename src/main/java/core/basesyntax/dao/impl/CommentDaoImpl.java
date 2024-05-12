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
            throw new RuntimeException("Can not create comment: " + entity, e);
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
            if (session != null) {
                return session.get(Comment.class, id);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot get comment with id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            if (session != null) {
                return session.createQuery("from Comment", Comment.class).getResultList();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Can not get comments", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();;
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not remove comment:" + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
