package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    private static final String GET_ALL_SMILES_SQL = "FROM Smile";
    private Session session;
    private Transaction transaction;

    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not add smile", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (id <= 0L) {
            throw new IllegalArgumentException("ID cannot be negative or zero");
        }
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Could not get smile", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Smile> getAll() {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            return session.createQuery(GET_ALL_SMILES_SQL, Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Could not get smiles", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
