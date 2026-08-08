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
    public Comment create(Comment entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DataProcessingException("Can not add comment " + entity + " into DB.", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (EntityManager entityManager = factory.openSession()) {
            return entityManager.find(Comment.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can not get comment by id" + id + "from DB", e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (EntityManager entityManager = factory.openSession()) {
            Query query = entityManager.createQuery("from Comment", Comment.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can not get all comments", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DataProcessingException("Can not remove comment " + entity + " from DB.", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
