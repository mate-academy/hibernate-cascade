package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.exception.DataProcessingException;
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
            if (entity.getId() == null) {
                session.persist(entity);
            } else {
                session.merge(entity);
            }
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Could not create new "
                    + entity.getClass().getSimpleName()
                    + ", "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(Comment.class, id);
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Can't get "
                    + Comment.class.getSimpleName()
                    + " by id: " + id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Comment> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            return session.createQuery("from Comment", Comment.class).list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get any "
                    + Comment.class.getSimpleName());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Comment managedComment = session.find(Comment.class, entity.getId());
            if (managedComment != null) {
                managedComment.getSmiles().clear();
                session.remove(managedComment);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Could not remove "
                    + entity.getClass().getSimpleName()
                    + ", "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
