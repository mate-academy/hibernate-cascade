package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(String.format(
                    "An error occurred while trying to add a smile %s to the database",
                    smile), ex);
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(Smile.class, id))
                    .orElseThrow(() -> new RuntimeException(String.format(
                    "An error occurred while trying to retrieve a smile "
                            + "with ID %d from the database", id)));
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM Smile a", Smile.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while trying to retrieve "
                    + "all smiles from the database", ex);
        }
    }
}
