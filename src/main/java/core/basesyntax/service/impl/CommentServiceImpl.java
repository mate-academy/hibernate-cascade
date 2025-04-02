package core.basesyntax.service.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.lib.Inject;
import core.basesyntax.model.Comment;
import core.basesyntax.service.CommentService;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    @Inject
    private CommentDao commentDao;

    @Override
    public Comment create(Comment entity) {
        return commentDao.create(entity);
    }

    @Override
    public Comment get(Long id) {
        Comment comment = commentDao.get(id);
        if (comment == null) {
            throw new DataProcessingException("Comment not found for id: " + id);
        }
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = commentDao.getAll();
        if (comments == null) {
            throw new DataProcessingException("Comment not found for id's");
        }
        return comments;
    }

    @Override
    public void remove(Comment entity) {
        commentDao.remove(entity);
    }
}
