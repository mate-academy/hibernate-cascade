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
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot create comment", e);
        }
    }

    @Override
    public Comment get(Long id) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            Comment comment = session.get(Comment.class, id);
            transaction.commit();
            return comment;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot get comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            List<Comment> comments = session
                    .createQuery("FROM Comment", Comment.class)
                    .getResultList();
            transaction.commit();
            return comments;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot get all comments", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            Comment managedComment = session.get(Comment.class, entity.getId());

            if (managedComment != null) {
                session.remove(managedComment);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot remove comment with id: " + entity.getId(), e);
        }
    }
}
