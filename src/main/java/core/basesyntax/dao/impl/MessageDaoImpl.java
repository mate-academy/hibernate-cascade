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
        Transaction tr = null;
        try {
            session = factory.openSession();
            tr = session.beginTransaction();
            session.persist(entity);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            throw new RuntimeException("Cant create new message");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cant get message by id: " + id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Message", Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cant get all message", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction tr = null;
        try {
            session = factory.openSession();
            tr = session.beginTransaction();
            session.remove(session.contains(entity) ? entity : session.merge(entity));
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            throw new RuntimeException("Cant delete message");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
