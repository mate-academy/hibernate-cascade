package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();

            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't create new Message " + entity, e);
        }
    }

    @Override
    public Message get(Long id) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            Message message = session
                    .createQuery("SELECT message "
                            + "FROM Message message "
                            + "LEFT JOIN FETCH message.messageDetails "
                            + "WHERE message.id = :id", Message.class)
                    .setParameter("id", id)
                    .getSingleResult();
            transaction.commit();

            return message;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't get Message by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            List<Message> messages = session
                    .createQuery("SELECT DISTINCT message "
                                    + "FROM Message message "
                                    + "LEFT JOIN FETCH message.messageDetails",
                            Message.class)
                    .getResultList();
            transaction.commit();

            return messages;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't get Messages", e);
        }
    }

    @Override
    public void remove(Message entity) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            Message message = session.merge(entity);
            session.remove(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't remove Message " + entity, e);
        }
    }
}
