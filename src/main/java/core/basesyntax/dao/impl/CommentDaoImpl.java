package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;

import core.basesyntax.model.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't create Comment entity: " + entity + " , error: " + e);
        }
        return entity;
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
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't get Message entity with id: " + id + " , error: " + e);
        }
    }

    @Override
    public List<Comment> getAll() {
        String hql = "FROM Comment";
        try (Session session = factory.openSession()) {
            Query<Comment> query = session.createQuery(hql, Comment.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(
                    "CommentDaoImpl.getAll() failed to fetch users", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Can't remove Message entity: " + entity + " , error: " + e);
        }
    }
}
