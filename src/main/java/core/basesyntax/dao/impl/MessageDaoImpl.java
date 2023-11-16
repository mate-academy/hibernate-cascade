package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    private static final String GET_ALL_QUERY = "SELECT * FROM message";
    private static final String ADD_EXCEPTION_MESSAGE = "Failed to add message to DB ";
    private static final String GET_EXCEPTION_MESSAGE = "Failed to get message from DB by id=";
    private static final String REMOVE_EXCEPTION_MESSAGE = "Failed to remove message from DB ";
    private static final String GET_ALL_EXCEPTION_MESSAGE = "Failed to get all messages from DB ";

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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(ADD_EXCEPTION_MESSAGE + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(Message.class, id))
                    .orElseThrow(() -> new RuntimeException(GET_EXCEPTION_MESSAGE + id));
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(GET_ALL_QUERY, Message.class).getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException(GET_ALL_EXCEPTION_MESSAGE, e);
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(REMOVE_EXCEPTION_MESSAGE + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
