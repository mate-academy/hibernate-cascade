package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create entity to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Optional<Message> message;
        try (Session session = factory.openSession()) {
            message = Optional.ofNullable(session.get(Message.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can't get user from DB", e);
        }
        return message.get();
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        Optional<Message> message;
        int i = 1;
        try (Session session = factory.openSession()) {
            message = Optional.ofNullable(session.get(Message.class, i));
            while (message.isPresent()) {
                message = Optional.ofNullable(session.get(Message.class, i));
                messages.add(message.get());
                i++;
                message = Optional.ofNullable(session.get(Message.class, i));
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't get user from DB", e);
        }
        return messages;
    }

    @Override
    public void remove(Message entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove entity from DB", e);
        }
    }
}
