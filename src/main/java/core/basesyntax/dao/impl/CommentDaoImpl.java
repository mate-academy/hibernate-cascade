package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {

    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        List<Long> smileIds = entity.getSmiles().stream()
                .map(Smile::getId)
                .collect(Collectors.toList());

        entity.setSmiles(new java.util.ArrayList<>());

        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            for (Long id : smileIds) {
                Smile managed = session.find(Smile.class, id);
                entity.addSmile(managed);
            }

            session.persist(entity);
            transaction.commit();
            return entity;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't create entity: " + entity, e);
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.find(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get entity: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Comment", Comment.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all entities", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            Comment managed = session.find(Comment.class, entity.getId());

            if (managed != null) {
                for (Smile smile : managed.getSmiles()) {
                    smile.setComment(null);
                    session.merge(smile);
                }
                session.remove(managed);
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove entity: " + entity, e);
        }
    }
}
