package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(entity);
            entity.setId(id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save message to DB");
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Session session = factory.openSession();
        Message message = session.get(Message.class, id);
        session.close();
        return message;
    }

    @Override
    public List<Message> getAll() {
        Session session = factory.openSession();
        String hql = "FROM Message";
        Query query = session.createQuery(hql);
        List<Message> messages = query.getResultList();
        session.close();
        return messages;
    }

    @Override
    public void remove(Message entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete message from DB");
        } finally {
            session.close();
        }
    }
}
