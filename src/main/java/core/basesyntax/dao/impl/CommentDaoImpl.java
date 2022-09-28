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
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create new comment: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.find(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get comment by ID: " + id, e);
        }
    }
    
    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("select c from Comment c", Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comments.", e);
        }
    }
    
    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete the comment: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
