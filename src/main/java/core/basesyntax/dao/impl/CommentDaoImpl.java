package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment item) {
        try {
            return super.create(item);
        } catch (Exception e) {
            throw new RuntimeException("Can't insert Content entity", e);
        }
    }

    @Override
    public Comment get(Long id) {
        return super.get(id, Comment.class);
    }

    @Override
    public List<Comment> getAll() {
        return super.getAll(Comment.class);
    }
}
