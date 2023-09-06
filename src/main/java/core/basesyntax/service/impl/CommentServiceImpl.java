package core.basesyntax.service.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import core.basesyntax.service.CommentService;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;
    private final SmileDao smileDao;

    public CommentServiceImpl(CommentDao commentDao, SmileDao smileDao) {
        this.commentDao = commentDao;
        this.smileDao = smileDao;
    }

    @Override
    public Comment create(Comment comment, List<Smile> smiles) {
        comment.setSmiles(smiles);
        return commentDao.create(comment);
    }

    @Override
    public void remove(Comment comment) {
        comment.setSmiles(List.of());
        commentDao.remove(comment);
    }

}
