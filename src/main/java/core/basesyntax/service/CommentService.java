package core.basesyntax.service;

import core.basesyntax.model.Comment;
import java.util.List;

public interface CommentService {
    Comment create(Comment entity);
    
    Comment get(Long id);
    
    List<Comment> getAll();
    
    void remove(Comment entity);
}
