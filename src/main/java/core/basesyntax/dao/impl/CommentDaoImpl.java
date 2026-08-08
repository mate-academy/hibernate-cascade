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
    private final SmileDaoImpl smileDao = new SmileDaoImpl(
            factory.openSession().getSessionFactory());

    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            List<Smile> smiles = new ArrayList<>();
            for (Smile smile : comment.getSmiles()) {
                Smile smileInDb = smileDao.get(smile.getId());
                if (smileInDb == null) {
                    throw new RuntimeException("Smile entity does not exist in the database");
                } else {
                    Smile managedSmile = (Smile) session.merge(smileInDb);
                    smiles.add(managedSmile);
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
            throw new RuntimeException("Can't insert Comment ", e);
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
            throw new RuntimeException("Can't get Comment by id: " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        String query = "SELECT * FROM comments";
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(query, Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all Comment ", e);
        }
    }

    @Override
    public void remove(Comment comment) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(comment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete Comment ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
