package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Message;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {

    public static final String ERROR_DURING_CREATION_MESSAGE =
            "Error during creation message -> %s";
    public static final String THERE_IS_NO_MESSAGE_WITH_SUCH_ID =
            "There is no message with such id -> %d";
    public static final String ERROR_DURING_RETRIEVING_MESSAGE_WITH_ID =
            "Error during retrieving message with id -> %d";
    public static final String ERROR_DURING_RETRIEVING_ALL_MESSAGES_FROM_DB =
            "Error during retrieving all messages from db";
    public static final String SELECT_ALL_MESSAGES = "SELECT m FROM Message m";
    public static final String ERROR_DURING_REMOVING_MESSAGE =
            "Error during removing message -> %s";

    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
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
                    ERROR_DURING_CREATION_MESSAGE.formatted(entity), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            Optional<Message> messageFromDB = Optional.ofNullable(session.get(Message.class, id));
            return messageFromDB.orElseThrow(() -> new EntityNotFoundException(
                    THERE_IS_NO_MESSAGE_WITH_SUCH_ID.formatted(id)));
        } catch (Exception e) {
            throw new DataProcessingException(
                    ERROR_DURING_RETRIEVING_MESSAGE_WITH_ID.formatted(id), e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(SELECT_ALL_MESSAGES, Message.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException(ERROR_DURING_RETRIEVING_ALL_MESSAGES_FROM_DB, e);
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException(
                    ERROR_DURING_REMOVING_MESSAGE.formatted(entity), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
