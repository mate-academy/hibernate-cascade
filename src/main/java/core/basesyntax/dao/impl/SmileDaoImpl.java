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
            assert transaction != null;
            transaction.rollback();
            throw new RuntimeException("Cant add Smile to DB" + e);
        } finally {
            assert session != null;
            session.close();
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cant get Smile from DB" + e);
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            Query<Smile> allSmilesFromDb = session.createQuery("from Smile", Smile.class);
            return allSmilesFromDb.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cant get all Smiles from DB" + e);
        } finally {
            assert session != null;
            session.close();
        }
    }
}
