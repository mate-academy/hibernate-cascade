package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;
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
            throw new DataProcessingException("Failed to save a comment: " + comment, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return comment;
    }

    @Override
    public Comment get(Long id) {
        try (EntityManager entityManager = factory.openSession()) {
            return entityManager.find(Comment.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get a comment with id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (EntityManager entityManager = factory.openSession()) {
            Query query = entityManager.createQuery("from Comment", Comment.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get all comments", e);
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
            throw new DataProcessingException("Failed to remove a comment: " + comment, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
