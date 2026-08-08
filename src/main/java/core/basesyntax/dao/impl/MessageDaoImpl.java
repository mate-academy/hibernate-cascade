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
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save message", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            Message message = session.get(Message.class, id);
            return message;
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            List<Message> message = session.createQuery("FROM Message", Message.class).list();
            return message;
        }
    }

    @Override
    public void remove(Message entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Can't remove message " + entity, e);
        }

    }
}
