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
        Transaction transaction = null;
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
            throw new RuntimeException("Can not save Comment object with data: "
                    + entity + " to Db", e);
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
            throw new RuntimeException("Can not get Comment object with id: "
                    + id + " from DB", e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Comment", Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can not get list of Comment objects from DB", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Comment managedComment = session.get(Comment.class, entity.getId());
            if (managedComment != null) {
                session.remove(managedComment);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not remove Comment object with data: "
                    + entity + " from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
