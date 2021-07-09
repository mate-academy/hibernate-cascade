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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save a Comment entity with ID:" + entity.getId()
                    + "\t Exception: ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Session session = factory.openSession();
        Comment entity = session.get(Comment.class, id);
        session.close();
        return entity;
    }

    @Override
    public List<Comment> getAll() {
        Session session = factory.openSession();
        List<Comment> entityList =
                session.createQuery("SELECT a FROM Comment a", Comment.class).getResultList();
        session.close();
        return entityList;
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
            throw new RuntimeException("Can't remove a Comment entity with ID:" + entity.getId()
                    + "\t Exception: ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
