package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.MessageDetails;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {

    public static final String ERROR_DURING_RETRIEVING_MESSAGE_DETAILS_WITH_ID =
            "Error during retrieving message details with id -> %d";
    public static final String THERE_IS_NO_MESSAGE_DETAILS_WITH_SUCH_ID =
            "There is no message details with such id -> %d";
    public static final String ERROR_DURING_CREATION_OF_MESSAGE_DETAILS =
            "Error during creation of message details -> %s";

    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
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
            throw new DataProcessingException(
                    ERROR_DURING_CREATION_OF_MESSAGE_DETAILS.formatted(entity), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            Optional<MessageDetails> mdFromDB = Optional.ofNullable(
                    session.get(MessageDetails.class, id));
            if (mdFromDB.isPresent()) {
                return mdFromDB.get();
            }
            return null;
        } catch (Exception e) {
            throw new DataProcessingException(
                    ERROR_DURING_RETRIEVING_MESSAGE_DETAILS_WITH_ID.formatted(id), e);
        }
    }
}
