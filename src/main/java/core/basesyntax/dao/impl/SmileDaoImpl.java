package core.basesyntax.dao.impl;

import java.util.List;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import org.hibernate.HibernateException;
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
            session.save(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't create new smile in DB. Smile: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile = null;
        try (Session session = factory.openSession()) {
            smile = session.get(Smile.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException("Couldn't get the smile from DB by id: " + id, e);
        }
        if (smile == null) {
            throw new RuntimeException("There is no smile in DB by such id: " + id);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> getAllSmilesQuery =
                    session.createQuery("from Smile", Smile.class);
            return getAllSmilesQuery.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Couldn't get all smiles from DB");
        }
    }
}
