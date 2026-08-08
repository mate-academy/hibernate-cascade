package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        Transaction transaction = null;
        try (var session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create Comment entity: " + entity, e);
        }
    }

    @Override
    public Comment get(Long id) {
        try (var session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Comment entity by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        String query = "FROM Comment";
        try (var session = factory.openSession()) {
            return session.createQuery(query, Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Comment entities", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        try (var session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to remove Comment entity: " + entity, e);
        }
    }
}
