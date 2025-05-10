package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    private final SessionFactory sessionFactory;

    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Message create(Message entity) {
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to create Message", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Message get(Long id) {
        Session session = getSession();
        try {
            return session.get(Message.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Message> getAll() {
        Session session = getSession();
        try {
            return session.createQuery("FROM Message").list();
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(Message message) {
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Message persistentMessage = session.get(Message.class, message.getId());
            if (persistentMessage != null) {
                session.delete(persistentMessage);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to remove Message", e);
        } finally {
            session.close();
        }
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }
}
