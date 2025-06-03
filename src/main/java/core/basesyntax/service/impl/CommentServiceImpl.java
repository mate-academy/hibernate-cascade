package core.basesyntax.service.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import core.basesyntax.service.CommentService;

public class CommentServiceImpl implements CommentService {
    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public void remove(Comment comment) {
        comment.getSmiles().clear();
        commentDao.remove(comment);
    }
}
