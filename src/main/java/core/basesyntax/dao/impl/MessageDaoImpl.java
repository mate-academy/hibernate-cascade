package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Log4j
public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message message) {
        log.info("Calling a create() method of MessageDaoImpl class");
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(message);
            log.info("Attempt to store message " + message + " in db.");
            transaction.commit();
            return message;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create message entity. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        log.info("Calling a get() method of MessageDaoImpl class");
        Session session = factory.openSession();
        return session.get(Message.class, id);
    }

    @Override
    public List<Message> getAll() {
        log.info("Calling a getAll() method of MessageDaoImpl class");
        try (Session session = factory.openSession()) {
            CriteriaQuery<Message> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Message.class);
            criteriaQuery.from(Message.class);
            log.info("Attempt to retrieve collection of messages from db.");
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all messages. ", e);
        }
    }

    @Override
    public void remove(Message message) {
        log.info("Calling a remove() method of MessageDaoImpl class");
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.remove(message);
            log.info("Attempt to remove message from db.");
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Can't delete message entity. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
