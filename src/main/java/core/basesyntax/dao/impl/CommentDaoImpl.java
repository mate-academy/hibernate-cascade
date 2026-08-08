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
        return sessionContainer(session -> {
            session.persist(entity);
            return entity;
        });
    }

    @Override
    public Comment get(Long id) {
        return sessionContainer(session -> session.get(Comment.class, id));
    }

    @Override
    public List<Comment> getAll() {
        return sessionContainer(session ->
                session.createQuery("FROM Comment", Comment.class)
                        .getResultList());
    }

    @Override
    public void remove(Comment entity) {
        sessionContainer(session -> {
            session.remove(entity);
            return entity;
        });
    }
}
