package core.basesyntax.dao.impl;

import static core.basesyntax.HibernateUtil.getSessionFactory;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Can not create Comment: " + entity, e);
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can not get Comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = getSessionFactory().openSession()) {
            String sql = "FROM Comment";
            return session.createQuery(sql, Comment.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can not get all Comments", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            Comment managedComment = session.get(Comment.class, entity.getId());
            if (managedComment != null) {
                session.delete(managedComment);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Can not remove Comment: " + entity, e);
        }
    }
}
