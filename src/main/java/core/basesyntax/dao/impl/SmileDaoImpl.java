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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create the comment. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(Smile.class, id);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get a Smile. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;
        List<Smile> results;
        try {
            session = factory.openSession();
            results = session.createQuery("SELECT a FROM Smile a", Smile.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get all Smiles. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return results;
    }
}
