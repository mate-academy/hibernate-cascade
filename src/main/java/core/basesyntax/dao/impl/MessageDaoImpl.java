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
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can not save entity Message by id: " + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entity;
    }

    @Override
    public Message get(Long id) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            return session.get(Message.class, id);
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can not find entity Message by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Message", Message.class).list();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can not find any Messages", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can not remove entity Message by id: " + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
