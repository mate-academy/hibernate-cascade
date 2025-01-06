package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Comment get(Long id) {
        try (var session = factory.openSession()) {
            return session.get(Comment.class, id);
        }

    }

    @Override
    public List<Comment> getAll() {
        try (var session = factory.openSession()) {
            return session.createQuery("from Comment", Comment.class).list();
        }
    }

    @Override
    public void remove(Comment entity) {
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

