package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not create a smile ", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Could not get an id from smile " + ex);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("SELECT * FROM Smile", Smile.class).getResultList();
        } catch (RuntimeException ex) {
            throw new RuntimeException("Could not get a list of all smiles " + ex);
        }
    }
}
