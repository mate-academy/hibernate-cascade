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
        return super.createEntity(entity);
    }

    @Override
    public Comment get(Long id) {
        return super.getEntity(Comment.class, id);
    }

    @Override
    public List<Comment> getAll() {
        return super.getAllEntities(Comment.class, "Comment");
    }

    @Override
    public void remove(Comment entity) {
        super.removeEntity(entity);
    }
}
