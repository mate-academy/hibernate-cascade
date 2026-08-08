package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import jakarta.persistence.Table;
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
            if (entity.getMessageDetails() != null) {
                session.persist(entity.getMessageDetails());
            }
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant create message: "
                    + entity.getContent());
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
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cant get message with id: " + id);
        }
    }

    @Override
    public List<Message> getAll() {
        String tableName = null;
        try (Session session = factory.openSession()) {
            Table tableAnnotation = Comment.class.getAnnotation(Table.class);
            tableName = tableAnnotation != null ? tableAnnotation.name()
                    : Comment.class.getSimpleName();
            return session.createNativeQuery("SELECT * FROM messages", Message.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cant get all data from " + tableName);
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant remove message: "
                    + entity.getContent());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
