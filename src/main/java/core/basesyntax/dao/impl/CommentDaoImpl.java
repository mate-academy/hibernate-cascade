package core.basesyntax.dao.impl;

import static core.basesyntax.util.HqlQueries.GET_ALL_COMMENTS;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        return executeInsideTransaction(session -> {
            session.persist(entity);
            return entity;
        });
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(GET_ALL_COMMENTS, Comment.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comments", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        executeInsideTransaction(session -> {
            session.remove(entity);
        });
    }
}
