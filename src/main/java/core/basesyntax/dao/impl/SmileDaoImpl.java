package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save to DB smile = " + entity.toString(), e);
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
            Smile smile = session.get(Smile.class, id);
            if (smile != null) {
                return smile;
            } else {
                throw new EntityNotFoundException("Not found in DB smile with id = " + id);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get from DB smile with id = " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            List<Smile> smiles = session.createQuery("from Smile").list();
            if (smiles != null) {
                return smiles;
            } else {
                // throw new EntityNotFoundException("Not found in DB all smiles");
                return new ArrayList<>();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get from DB all smiles", e);
        }
    }
}
