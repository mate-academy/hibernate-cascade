package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
            entityManager = factory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Cannot create comment: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.find(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
    }

    @Override
    public void remove(Comment entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Cannot remove comment: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
