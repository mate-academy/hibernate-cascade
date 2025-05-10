package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public Comment create(Comment entity) {
        return null;
    }

    @Override
    public Comment get(Long id) {
        return null;
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public void remove(Comment entity) {

    }
}
