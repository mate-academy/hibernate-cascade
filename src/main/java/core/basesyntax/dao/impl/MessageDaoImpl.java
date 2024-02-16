package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
        SessionFactory sessionFactory;
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory = factory;
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create message '" + entity + "' in DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Message message;
        SessionFactory sessionFactory;
        Session session = null;
        try {
            sessionFactory = factory;
            session = sessionFactory.openSession();
            message = session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message '" + "' from DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        criteriaQuery.from(Message.class);
        return session.createQuery(criteriaQuery).list();
    }

    @Override
    public void remove(Message entity) {
        SessionFactory sessionFactory;
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory = factory;
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove message '" + entity + "' from DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
