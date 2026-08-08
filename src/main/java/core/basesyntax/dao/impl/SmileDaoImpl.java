package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Smile;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {

    public static final String ERROR_DURING_CREATION_SMILE =
            "Error during creation smile -> %s";
    public static final String NO_SMILE_WITH_SUCH_ID =
            "No smile with such id -> %d";
    public static final String ERROR_DURING_RETRIEVING_SMILE_WITH_ID =
            "Error during retrieving smile with id -> %d";
    public static final String SELECT_ALL_SMILE =
            "SELECT s FROM Smile s";

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
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException(ERROR_DURING_CREATION_SMILE.formatted(entity),
                    e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            Optional<Smile> smileFromDB = Optional.ofNullable(session.get(Smile.class, id));
            return smileFromDB.orElseThrow(
                    () -> new EntityNotFoundException(NO_SMILE_WITH_SUCH_ID.formatted(id)));
        } catch (Exception e) {
            throw new DataProcessingException(
                    ERROR_DURING_RETRIEVING_SMILE_WITH_ID.formatted(id), e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT_ALL_SMILE, Smile.class)
                    .getResultList();
        }
    }
}
