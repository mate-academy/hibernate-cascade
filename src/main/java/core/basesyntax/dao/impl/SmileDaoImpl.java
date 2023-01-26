package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;

import java.util.ArrayList;
import java.util.Collections;
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
            throw new RuntimeException("Can't create smile entity " + entity, e);
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
        try (Session session = factory.openSession()){
            smile = session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile by id " + id, e);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smiles = new ArrayList<>();
        try (Session session = factory.openSession()){
            Query<Smile> getAllSmiles = session.createQuery("FROM Smile", Smile.class);
            return getAllSmiles.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles", e);
        }
    }
}
