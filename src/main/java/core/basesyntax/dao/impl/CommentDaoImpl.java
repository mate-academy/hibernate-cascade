package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import jakarta.persistence.Table;
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
            throw new RuntimeException("cant create comment: "
                    + entity.getContent());
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
            throw new RuntimeException("Cant get comment with id: " + id);
        }
    }

    @Override
    public List<Comment> getAll() {
        String tableName = null;
        try (Session session = factory.openSession()) {
            Table tableAnnotation = Comment.class.getAnnotation(Table.class);
            tableName = tableAnnotation != null ? tableAnnotation.name()
                    : Comment.class.getSimpleName();
            return session.createNativeQuery("SELECT * FROM comments",
                            Comment.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cant get all data from " + tableName);
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
            throw new RuntimeException("Cant remove comment: "
                    + entity.getContent());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
