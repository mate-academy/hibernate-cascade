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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        throw new RuntimeException("Can't create a comment " + entity);
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get a comment with id = " + id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Comment", Comment.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all comments");
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Comment comment = session.get(Comment.class, entity.getId());
            if (comment != null) {
                session.remove(comment);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove a comment " + entity);
        }
    }
}
