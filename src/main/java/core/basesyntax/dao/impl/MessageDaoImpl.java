package core.basesyntax.dao.impl;

import java.util.ArrayList;
import java.util.List;
import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't create message");
            }
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            Message message = session.getReference(Message.class, id);
            transaction.commit();
            return message;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't get comment by id");
            }
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Message> getAll() {
        Transaction transaction = null;
        List<Message> messages = new ArrayList<>();
        Session session = factory.openSession();
        try {
            transaction = session.getTransaction();
            transaction.begin();
            messages = session.createQuery("FROM Smile", Message.class).getResultList();
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't get all smiles");
        } finally {
            session.close();
        }
        return messages;
    }

    @Override
    public void remove(Message entity) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove comment");
        } finally {
            session.close();
        }
    }
}
