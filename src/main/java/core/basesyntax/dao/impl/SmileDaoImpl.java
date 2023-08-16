package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Smile create(Smile smile) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(smile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add new smile " + smile + " to the DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    public Smile get(Long id) {
        Session session = null;
        Transaction transaction = null;
        Smile receivedSmile = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            receivedSmile = session.get(Smile.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't get new smile with id" + id + " from the DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return receivedSmile;
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> getAllCommentsQuery = session.createQuery("from Smile", Smile.class);
            return getAllCommentsQuery.getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Can't get all comments from DB", ex);
        }
    }
}
