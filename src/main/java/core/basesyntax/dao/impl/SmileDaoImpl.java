package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Comment;
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
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(entity);

        transaction.commit();
        session.close();
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Session session = factory.openSession();

        try {
            Smile smile = session.find(Smile.class, id);
            return smile;
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get a smile by id " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Smile> getAll() {
        Session session = factory.openSession();
        List<Smile> smiles = session.createQuery("From Smile", Smile.class).list();
        session.close();
        return smiles;
    }

    @Override
    public void remove(Smile smile) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            session.remove(smile);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to remove comment " + smile, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
