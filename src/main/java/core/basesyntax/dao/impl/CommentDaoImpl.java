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
    public Comment create(Comment comment) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            List<Smile> inputSmiles = comment.getSmiles();
            List<Smile> validatedSmiles = new ArrayList<>();

            if (inputSmiles != null) {
                for (Smile smile : inputSmiles) {
                    Long id = smile.getId();
                    if (id == null) {
                        throw new RuntimeException("Only existing smiles allowed.");
                    }
                    Smile fromDb = session.get(Smile.class, id);
                    if (fromDb == null) {
                        throw new RuntimeException("Smile with ID " + id + " not found in DB.");
                    }
                    validatedSmiles.add(fromDb);
                }
            }

            comment.setSmiles(validatedSmiles);
            session.persist(comment);
            transaction.commit();
            return comment;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create Comment entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get Comment entity by id " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Comment", Comment.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all Comment entities", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(session.get(Comment.class, entity.getId()));
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Can't remove Comment entity", e);
        }
    }
}
