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
        factory.inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public Comment get(Long id) {
        return factory.fromSession(session -> session.get(Comment.class, id));
    }

    @Override
    public List<Comment> getAll() {
        return factory.fromSession(
                session -> session.createQuery("from Comment", Comment.class).getResultList());
    }

    @Override
    public void remove(Comment entity) {
        factory.inTransaction(session -> session.remove(entity));
    }
}
