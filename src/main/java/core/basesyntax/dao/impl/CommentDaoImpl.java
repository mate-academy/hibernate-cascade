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
        return createEntity(entity);
    }

    @Override
    public Comment get(Long id) {
        return getEntity(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        return getAllEntities(Comment.class);
    }

    @Override
    public void remove(Comment entity) {
        removeEntity(entity);
    }
}
