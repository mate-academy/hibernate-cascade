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
    public Smile create(Smile smile) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(smile);
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction was rollback, " + exception);
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        Smile smile;
        try (Session session = factory.openSession()) {
            smile = session.get(Smile.class, id);
        } catch (RuntimeException exception) {
            throw new RuntimeException("There is no way to get Smile by id: "
                    + id + ", ", exception);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smiles;
        try (Session session = factory.openSession()) {
            smiles = session.createQuery("FROM Smile", Smile.class).getResultList();
        } catch (RuntimeException exception) {
            throw new RuntimeException("There is no way to get all smiles, "
                    + exception);
        }
        return smiles;
    }
}
