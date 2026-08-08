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
        return (Comment) super.createEntity(entity);
    }

    @Override
    public Comment get(Long id) {
        return (Comment) super.getEntity(id, Comment.class);
    }

    @Override
    public List<Comment> getAll() {
        return (List<Comment>) super.getAllEntities(Comment.class);
    }

    @Override
    public void remove(Comment entity) {
        super.removeEntity(entity);
    }
}
