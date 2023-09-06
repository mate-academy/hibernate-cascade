package core.basesyntax.service;

import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import java.util.List;

public interface CommentService {

    public Comment create(Comment comment, List<Smile> smiles);

    void remove(Comment comment);
}
