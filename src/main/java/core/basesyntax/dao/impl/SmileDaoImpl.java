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
    public Smile create(Smile smile) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(smile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save smile in DB", e);
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        Smile smile;
        try (Session session = factory.openSession()) {
            smile = session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile with id=" + id + " from DB", e);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> getAllSmilesQuery = session.createQuery(
                    "from Smile ", Smile.class);
            return getAllSmilesQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles from DB", e);
        }
    }
}
