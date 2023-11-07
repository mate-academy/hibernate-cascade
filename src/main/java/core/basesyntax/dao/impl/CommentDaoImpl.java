package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import org.hibernate.SessionFactory;

import java.util.List;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Comment.class);
    }

    @Override
    public Comment create(Comment entity) {
        return super.create(entity);
    }

    @Override
    public Comment get(Long id) {
        return super.get(id);
    }

    @Override
    public List<Comment> getAll() {
        return super.getAll();
    }

    @Override
    public void remove(Comment entity) {
        super.remove(entity);
    }
}
