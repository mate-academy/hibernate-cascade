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
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant add message to db", e);
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile = null;
        try (Session session = factory.openSession()) {
            smile = session.get(Smile.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Cant get an message from db", e);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;

        try {
            session = factory.openSession();
            Query getAllSmilesQuery = session.createQuery("from Smile",Smile.class);
            return getAllSmilesQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cant get all messages from db", e);
        }
    }
}
