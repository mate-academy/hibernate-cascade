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
        Session session = null;
        Transaction transaction = null;
        try {
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save a smile " + entity + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = super.factory.openSession()) {
            Smile smile = session.get(Smile.class, id);
            return smile;
        } catch (Exception e) {
            throw new RuntimeException("Can't get message details by id " + id + " from DB", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = super.factory.openSession()) {
            Query<Smile> getAllQuery = session.createQuery("from Smile", Smile.class);
            return getAllQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong...", e);
        }
    }
}
