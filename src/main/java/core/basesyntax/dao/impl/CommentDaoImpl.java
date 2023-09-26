package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        factory.inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find comment with id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        Query<Comment> fromComment = factory.openSession()
                .createQuery("FROM Comment", Comment.class);
        return fromComment.getResultList();
    }

    @Override
    public void remove(Comment entity) {
        factory.inTransaction(session -> session
                .remove(entity));
    }
}
