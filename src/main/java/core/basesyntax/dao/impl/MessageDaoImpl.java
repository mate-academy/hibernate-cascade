package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Log4j
public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                log.debug("Transaction creation for " + entity.toString()
                        + " has been rollbacked.", e);
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Content entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        log.debug("Entity " + entity.toString() + " created");
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException("Can't get message id=" + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        List<Message> messageList = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query<Message> getAllMessagesQuery =
                    session.createQuery("from Message ", Message.class);
            messageList = getAllMessagesQuery.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Couldn't get all comments", e);
        }
        return messageList;
    }

    @Override
    public void remove(Message entity) {
        Transaction transaction;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Message message = session.find(Message.class, entity.getId());
            if (message != null) {
                session.remove(message);
                transaction.commit();
            }
        } catch (HibernateException e) {
            throw new RuntimeException("Message not deleted id=" + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        log.debug("Message " + entity.toString() + " has been removed");
    }
}
