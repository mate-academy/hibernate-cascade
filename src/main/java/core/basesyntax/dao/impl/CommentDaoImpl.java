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
        Session session = null;
        Transaction tx = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can not save entity Comment by id: " + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entity;
    }

    @Override
    public Comment get(Long id) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            return session.get(Comment.class, id);
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can not find entity Comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Comment", Comment.class).list();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can not find any Comments", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can not remove entity Comment by id: " + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
