package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class CommentDaoImplImpl extends GenericDaoImpl<Comment> implements CommentDao {
    public CommentDaoImplImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get Smile from DB with id " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            Query<Comment> getAllComments =
                    session.createQuery("SELECT c FROM Comment c", Comment.class);
            return getAllComments.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comments from DB");
        }
    }
}
