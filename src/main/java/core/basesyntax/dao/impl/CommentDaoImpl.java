package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment comment) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = factory.openSession();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't create comment " + comment, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        EntityManager entityManager = null;
        try {
            entityManager = factory.openSession();
            return entityManager.find(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get comment by ID " + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<Comment> getAll() {
        EntityManager entityManager = null;
        try {
            entityManager = factory.openSession();
            return entityManager.createQuery("FROM Comment e", Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get all comments", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public void remove(Comment comment) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = factory.openSession();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't delete comment " + comment, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
