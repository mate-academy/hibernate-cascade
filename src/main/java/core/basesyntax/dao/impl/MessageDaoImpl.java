package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
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
            MessageDetails messageDetails = entity.getMessageDetails();
            if (messageDetails != null) {
                session.persist(messageDetails);
            }
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create massage in db: " + entity, e);
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
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message from db by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        String getAllMessageQuery = "FROM Message";
        try (Session session = factory.openSession()) {
            return session.createQuery(getAllMessageQuery, Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all messages from db", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            MessageDetails messageDetails = entity.getMessageDetails();
            if (messageDetails != null) {
                session.remove(messageDetails);
            }
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete massage from db: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
