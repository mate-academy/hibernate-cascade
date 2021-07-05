package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.exceptions.DataProcessingException;
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
            throw new DataProcessingException("Can't insert Smile entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get Smile with id"
                    + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> getSmiles = session.createQuery("from Smile", Smile.class);
            return getSmiles.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get all Smiles", e);
        }
    }
}
