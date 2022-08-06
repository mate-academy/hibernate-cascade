package core.basesyntax.dao;

import core.basesyntax.model.Comment;

import java.util.List;

public interface CommentDao extends GenericDao<Comment> {
    @Override
    default Comment create(Comment entity) {
        return null;
    }

    @Override
    default Comment get(Long id) {
        return null;
    }

    @Override
    default List<Comment> getAll() {
        return null;
    }

    @Override
    default void remove(Comment entity) {

    }
}
