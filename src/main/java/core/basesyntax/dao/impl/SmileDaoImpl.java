package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    private Session session = null;
    private Transaction transaction = null;

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
            session.close();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Error creating entity to the database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("can't get an Entity from the DB", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smiles;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            smiles = session.createQuery("FROM Smile", Smile.class).getResultList();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error retrieving all entities from the database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smiles;
    }
}
