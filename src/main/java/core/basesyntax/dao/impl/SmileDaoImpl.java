package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import javax.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (PersistenceException persistenceException) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't save smile: "
                    + entity, persistenceException);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile;
        try (Session session = factory.openSession()) {
            smile = session.get(Smile.class, id);
        } catch (PersistenceException persistenceException) {
            throw new RuntimeException("Couldn't get smile by id: "
                    + id, persistenceException);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> allSmiles;
        try (Session session = factory.openSession()) {
            Query<Smile> getAllSmilesQuery = session
                    .createQuery("from Smile", Smile.class);
            allSmiles = getAllSmilesQuery.getResultList();
        } catch (PersistenceException persistenceException) {
            throw new RuntimeException("Couldn't get all smiles from DB",
                    persistenceException);
        }
        return allSmiles;
    }
}
