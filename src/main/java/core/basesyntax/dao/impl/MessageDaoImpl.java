package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    private static final String EXCEPTION_CAN_T_CREATE_MESSAGE_MESSAGE =
            "Can not to create message with id: ";
    private static final String EXCEPTION_CAN_T_GET_MESSAGE_MESSAGE =
            "Can not to get message with id: ";
    private static final String EXCEPTION_CAN_T_GET_ALL_MESSAGES_MESSAGE =
            "Can not to get all messages";
    private static final String EXCEPTION_CAN_T_REMOVE_MESSAGE_MESSAGE =
            "Can not to remove message with id: ";
    private static final String QUERY_GET_ALL_MESSAGES = "SELECT * FROM messages";

    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
                return entity;
            } catch (Exception e) {
                transaction.rollback();
            }
            throw new RuntimeException(EXCEPTION_CAN_T_CREATE_MESSAGE_MESSAGE + entity.getId());
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException(EXCEPTION_CAN_T_GET_MESSAGE_MESSAGE + id);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(QUERY_GET_ALL_MESSAGES, Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(EXCEPTION_CAN_T_GET_ALL_MESSAGES_MESSAGE, e);
        }
    }

    @Override
    public void remove(Message entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.remove(entity);
                transaction.commit();
            } catch (Exception e) {
                throw new RuntimeException(EXCEPTION_CAN_T_REMOVE_MESSAGE_MESSAGE
                        + entity.getId(), e);
            }
        }
    }
}
