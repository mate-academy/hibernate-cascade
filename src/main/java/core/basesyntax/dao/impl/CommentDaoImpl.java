package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        try {
            return factory.fromTransaction(session -> {
                session.persist(entity);
                return entity;
            });
        } catch (Exception e) {
            throw new RuntimeException("Can't create Comment: " + entity, e);
        }
    }

    @Override
    public Comment get(Long id) {
        try {
            return factory.fromSession(session -> session.get(Comment.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can't get comment by id " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try {
            return factory.fromSession(session -> session.createQuery("FROM Comment", Comment.class)
                    .list());
        } catch (Exception e) {
            throw new RuntimeException("Can't get list of Comments from db", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        try {
            factory.inTransaction(session -> session.remove(entity));
        } catch (Exception e) {
            throw new RuntimeException("Can't remove comment: " + entity, e);
        }
    }
}
