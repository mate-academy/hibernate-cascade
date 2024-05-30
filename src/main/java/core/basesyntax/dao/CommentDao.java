package core.basesyntax.dao;

import core.basesyntax.model.Comment;
import java.util.List;

public interface CommentDao extends GenericDao<Comment> {

    Comment create(Comment comment);

    Comment get(Long id);

    List<Comment> getAll();

    void remove(Comment comment);
}
