package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Don`t save "
                    + entity + " to DB. Error: ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Don`t get comment by Id: "
                    + id + " from DB. Error: ", e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Query<Comment> query = session.createQuery("FROM Comment", Comment.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Don`t get all comments from DB. Error: ", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Don`t delete "
                    + entity + " to DB. Error: ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
