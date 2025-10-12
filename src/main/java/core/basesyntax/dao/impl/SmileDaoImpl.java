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

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't create smile " + smile + " , transaction rollback.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            Smile smile = session.find(Smile.class, id);
            if (smile != null) {
                return smile;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    "Can't get smile with id " + id + " from database", e);
        }
        return null;
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            List<Smile> smilesFromDb = session.createQuery("FROM Smile ", Smile.class).list();
            if (smilesFromDb != null) {
                return smilesFromDb;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(
                    "Can't get list of smiles from database", e);
        }
        return null;
    }
}
