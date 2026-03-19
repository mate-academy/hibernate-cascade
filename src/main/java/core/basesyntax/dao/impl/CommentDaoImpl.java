package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            entity.setSmiles(getManagedSmiles(session, entity.getSmiles()));
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create comment: " + entity, e);
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Comment", Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comments", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Comment comment = session.get(Comment.class, entity.getId());
            if (comment != null) {
                session.remove(comment);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove comment: " + entity, e);
        }
    }

    private List<Smile> getManagedSmiles(Session session, List<Smile> smiles) {
        List<Smile> managedSmiles = new ArrayList<>();
        if (smiles == null) {
            return managedSmiles;
        }
        for (Smile smile : smiles) {
            if (smile.getId() == null) {
                throw new RuntimeException("Smile must already exist in DB");
            }
            Smile managedSmile = session.get(Smile.class, smile.getId());
            if (managedSmile == null) {
                throw new RuntimeException("Smile with id " + smile.getId() + " doesn't exist");
            }
            managedSmiles.add(managedSmile);
        }
        return managedSmiles;
    }
}
