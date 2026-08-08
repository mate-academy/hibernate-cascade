package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
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
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create a message "
                    + entity.getClass().getSimpleName(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find message by id " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        Transaction transaction = null;
        List<Message> messagesList = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            messagesList = session.createQuery("FROM Message", Message.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all elements from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return messagesList;
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove the message from DB " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
