package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t create to DB" + entity.toString(),e);
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
            Comment comment = session.get(Comment.class,id);
            if (comment != null) {
                return comment;
            } else {
                throw new EntityNotFoundException("Can`t find by id in Db " + id);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get from DB comment with id = " + id, e);

        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            List<Comment> comments = session.createQuery("from Comment").list();
            if (comments != null) {
                return comments;
            } else {
                return new ArrayList<>();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can`t get from DB all comments", e);
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant remove from Db" + entity.toString(),e);

        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
