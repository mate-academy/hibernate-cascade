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

            List<Smile> smiles = new ArrayList<>();
            if (comment.getSmiles() != null) {
                for (Smile smile : comment.getSmiles()) {
                    // checking existing smiles in db and adding that in list
                    if (smile.getId() == null) {
                        throw new RuntimeException("Smile ID is null. "
                                + "Only existing smiles allowed.");
                    }

                    Smile smileFromDb = session.get(Smile.class, smile.getId());

                    if (smileFromDb == null) {
                        throw new RuntimeException("Smile with ID " + smile.getId()
                                + " not found in DB.");
                    }

                    smiles.add(smileFromDb);
                }
            }
            comment.setSmiles(smiles);
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
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Comment managedComment = session.get(Comment.class, entity.getId());
            if (managedComment != null) {
                session.remove(managedComment);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove Comment entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Smile> getManagedSmiles(List<Smile> allSmilesFromDb,
                                        List<Smile> smilesFromComment) {
        List<Smile> managedSmiles = new ArrayList<>();

        for (Smile smileFromComment : smilesFromComment) {
            for (Smile smileFromDb : allSmilesFromDb) {
                if (smileFromComment.getId() != null
                        && smileFromComment.getId().equals(smileFromDb.getId())) {
                    managedSmiles.add(smileFromDb);
                    break;
                }
            }
        }

        return managedSmiles;
    }
}
