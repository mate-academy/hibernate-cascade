package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
            session.persist(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save to DB message = " + entity.toString(), e);
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
            Message entity = session.get(Message.class, id);
            if (entity != null) {
                return entity;
            } else {
                throw new EntityNotFoundException("Not found in DB message with id = " + id);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get from DB message with id = " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            List<Message> entities = session.createQuery("from Message").list();
            if (entities != null) {
                return entities;
            } else {
                // throw new EntityNotFoundException("Not found in DB all smiles");
                return new ArrayList<>();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get from DB all messages ", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove from DB messege = " + entity.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
