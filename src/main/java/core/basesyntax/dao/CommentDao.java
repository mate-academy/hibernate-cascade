package core.basesyntax.dao;

import core.basesyntax.model.Comment;
import java.util.List;

public interface CommentDao extends GenericDao<Comment> {
    @Override
    Comment create(Comment entity);

    @Override
    Comment get(Long id);

    @Override
    List<Comment> getAll();

    @Override
    void remove(Comment entity);
}
