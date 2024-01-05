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

    @Override
    public Smile create(Smile entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to add"
                    + " new Smile to the DB: " + entity, e);
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to get MessageDetails"
                    + " from the DB with given ID: " + id);
        }
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smileList;
        try (Session session = factory.openSession()) {
            String hql = "FROM Smile";
            Query<Smile> query = session.createQuery(hql, Smile.class);
            smileList = query.list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all"
                    + " the Smiles from the DB", e);
        }
        return smileList;
    }
}
