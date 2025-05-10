package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
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
            throw new RuntimeException("Something is wrong with writing the SMILE: "
                    + System.lineSeparator()
                    + entity
                    + System.lineSeparator()
                    + " row to database. Closing connection and rolling back the transaction.", e);
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
            throw new RuntimeException(
                    "Something is wrong with getting data from DB for SMILE."
                            + "Maybe such ID as: " + id + " is absent in corresponding DB table",
                    e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("select a from smiles a", Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Something is wrong with getting ALL the data from DB for SMILE."
                            + "Maybe corresponding table is corrupted", e);
        }
    }
}
