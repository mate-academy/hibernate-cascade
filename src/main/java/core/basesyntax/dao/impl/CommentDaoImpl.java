package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
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
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
                return entity;
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't create Comment", e);
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Comment e left join fetch e.smiles where e.id = :id",
                    Comment.class).setParameter("id", id).uniqueResult();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get Comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Comment e left join fetch e.smiles order by e.id",
                    Comment.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all Comment entities", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Comment managed = session.get(Comment.class, entity.getId());
                if (managed != null) {
                    session.remove(managed);
                }
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't remove Comment", e);
        }
    }
}
