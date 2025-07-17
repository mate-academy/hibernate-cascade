package core.basesyntax.dao;

import core.basesyntax.model.Comment;
import java.util.List;

public interface CommentDao extends GenericDao<Comment> {
    public Comment create(Comment entity);

    public Comment get(Long id);

    public List<Comment> getAll();

    public void remove(Comment entity);
}
