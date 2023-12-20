package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import java.util.Optional;

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
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can not save entity: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        Session session = factory.openSession();
        return session.get(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Comment comment = session.get(Comment.class, 1L);
            return List.of(comment);
        } catch (Exception e) {
            throw new DataProcessingException("Can not find comment: ", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new DataProcessingException("Error while removing comment: " + entity, e);
        }
    }
}
