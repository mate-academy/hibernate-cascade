package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;

import java.security.spec.ECField;
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
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can`t save entity: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        Session session = factory.openSession();
        return session.get(Message.class, id);
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            Message message = session.get(Message.class, 1L);
            return List.of(message);
        } catch (Exception e) {
            throw new DataProcessingException("Can`t find message: ", e);
        }
    }

    @Override
    public void remove(Message entity) {
        try (Session session = factory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new DataProcessingException("Error while removing message: ", e);
        }
    }
}
