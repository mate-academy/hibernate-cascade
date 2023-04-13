package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;

        try {
            entityManager = factory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't save comment to DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return entity;
    }

    @Override
    public Comment get(Long id) {
        Comment comment;

        EntityManager entityManager = null;

        try {
            entityManager = factory.createEntityManager();
            comment = entityManager.find(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve comment from DB with id: " + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return comment;
    }

    @Override
    public List<Comment> getAll() {
        EntityManager entityManager = null;
        List<Comment> list = new ArrayList<>();

        try {
            entityManager = factory.createEntityManager();
            String hql = "FROM Comment";
            Query query = entityManager.createQuery(hql);
            list.addAll((List<Comment>) query.getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Could not get all comments from DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return list;
    }

    @Override
    public void remove(Comment entity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;

        try {
            entityManager = factory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't remove comment from DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
