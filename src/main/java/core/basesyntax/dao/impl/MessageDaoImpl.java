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
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't create comment ");
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
        Transaction tx = null;
        Message entity = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            entity = (Message) session.get(Message.class, id);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't get comment ");
        }
        return entity;
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        Transaction tx = null;
        List<Message> entityList = null;
        String sql = "SELECT * FROM messages";
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            entityList = session.createNativeQuery(sql, Message.class).list();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't get comment ");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entityList;
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't remove comment");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
