package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    private final SessionFactory sessionFactory = super.factory;

    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Message message = session.merge(entity);
            entity.setId(message.getId());
            entity.setMessageDetails(message.getMessageDetails());
            transaction.commit();
            return entity;
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while adding Message object:"
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(Message.class, id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
            Root<Message> root = criteriaQuery.from(Message.class);
            criteriaQuery.select(root);
            Query<Message> query = session.createQuery(criteriaQuery);
            return new ArrayList<>(query.getResultList());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while removing Message object: "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
