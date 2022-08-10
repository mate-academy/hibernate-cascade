package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.HibernateException;
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
            session = this.factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't create/add " + entity
                        + " in database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = this.factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get comment by id=" + id
                    + " from database", e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = this.factory.openSession()) {
            return session.createQuery(
                    "from Comment", Comment.class).getResultList();
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get all comments from database", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = this.factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't remove " + entity + " from database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
