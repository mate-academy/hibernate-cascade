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
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();

            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't create new Comment " + entity, e);
        }
    }

    @Override
    public Comment get(Long id) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            Comment comment = session
                    .createQuery("SELECT comment "
                                    + "FROM Comment comment "
                                    + "LEFT JOIN FETCH comment.smiles "
                                    + "WHERE comment.id = :id",
                            Comment.class)
                    .setParameter("id", id)
                    .getSingleResult();
            transaction.commit();

            return comment;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't get Comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            List<Comment> comments = session
                    .createQuery("SELECT DISTINCT comment "
                                    + "FROM Comment comment "
                                    + "LEFT JOIN FETCH comment.smiles",
                            Comment.class)
                    .getResultList();

            transaction.commit();

            return comments;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't get all Comments ", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            Comment comment = session.merge(entity);
            session.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't remove Comment " + entity, e);
        }
    }
}
