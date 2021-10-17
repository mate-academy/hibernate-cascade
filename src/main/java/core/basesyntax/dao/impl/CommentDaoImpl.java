package core.basesyntax.dao.impl;

import java.util.List;
import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
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
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            String hql = "FROM Comment WHERE id = :id";
            Query<Comment> query = session.createQuery(hql);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            String hql = "FROM Comment";
            Query<Comment> query = session.createQuery(hql);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
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
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
