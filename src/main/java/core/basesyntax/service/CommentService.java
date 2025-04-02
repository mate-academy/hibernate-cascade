package core.basesyntax.service;

import core.basesyntax.model.Comment;
import java.util.List;

public interface CommentService extends GenericService<Comment> {
    Comment create(Comment entity);

    Comment get(Long id);

    List<Comment> getAll();

    void remove(Comment entity);
}
