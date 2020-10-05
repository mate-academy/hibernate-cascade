package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao<Smile> implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile smile) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(smile);
            transaction.commit();
            return smile;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create the smile "
                    + smile + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Smile smile = session.get(Smile.class, id);
            transaction.commit();
            return smile;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get the smile with ID "
                    + id + " from the DB.", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> getAllSmilesQuery = session.createQuery("from Smile", Smile.class);
            return getAllSmilesQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all smiles from the DB.", e);
        }
    }
}
