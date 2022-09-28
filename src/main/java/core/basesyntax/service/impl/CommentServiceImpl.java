package core.basesyntax.service.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import core.basesyntax.service.CommentService;
import java.util.List;

public class CommentServiceImpl implements CommentService {
    private CommentDao dao;
    
    public CommentServiceImpl(CommentDao dao) {
        this.dao = dao;
    }
    
    @Override
    public Comment create(Comment entity) {
        return dao.create(entity);
    }
    
    @Override
    public Comment get(Long id) {
        return dao.get(id);
    }
    
    @Override
    public List<Comment> getAll() {
        return dao.getAll();
    }
    
    @Override
    public void remove(Comment entity) {
        dao.remove(entity);
    }
}
