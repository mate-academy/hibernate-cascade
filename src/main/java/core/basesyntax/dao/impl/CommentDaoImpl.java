package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    private static final String EXCEPTION_CAN_T_CREATE_COMMENT_MESSAGE =
            "Can not to create comment with id: ";
    private static final String EXCEPTION_CAN_T_GET_COMMENT_MESSAGE =
            "Can not to get comment with id: ";
    private static final String EXCEPTION_CAN_T_GET_ALL_COMMENT_MESSAGE =
            "Can not to get all comments";
    private static final String EXCEPTION_CAN_T_REMOVE_COMMENT_MESSAGE =
            "Can not to remove comment with id: ";
    private static final String QUERY_GET_ALL_COMMENTS = "SELECT * FROM comments";

    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
                return entity;
            } catch (Exception e) {
                transaction.rollback();
            }
            throw new RuntimeException(EXCEPTION_CAN_T_CREATE_COMMENT_MESSAGE + entity.getId());
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException(EXCEPTION_CAN_T_GET_COMMENT_MESSAGE + id);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(QUERY_GET_ALL_COMMENTS, Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(EXCEPTION_CAN_T_GET_ALL_COMMENT_MESSAGE, e);
        }
    }

    @Override
    public void remove(Comment entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.remove(entity);
                transaction.commit();
            } catch (Exception e) {
                throw new RuntimeException(EXCEPTION_CAN_T_REMOVE_COMMENT_MESSAGE
                        + entity.getId(), e);
            }
        }
    }
}
