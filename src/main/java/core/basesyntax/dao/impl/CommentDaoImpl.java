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
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Content entity");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get comment");
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Query<Comment> fromSmile = session.createQuery("from Comment ", Comment.class);
            return fromSmile.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comment");
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Comment comment = session.get(Comment.class, entity.getId());
            session.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove comment");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
