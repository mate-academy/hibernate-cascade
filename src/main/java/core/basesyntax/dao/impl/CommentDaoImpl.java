package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.ArrayList;
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
                throw new RuntimeException("Can`t save comment " + entity + " to DB");
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Comment get(Long id) {
        Comment comment = null;
        try (Session session = factory.openSession()) {
            comment = session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can`t find comment with id = " + id + " in Db");
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> list = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query<Comment> query = session.createQuery("FROM Comment", Comment.class);
            list = query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can`t find any comments in Db");
        }
        return list;
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
            throw new RuntimeException("Can`t remove comment " + entity + " from DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
