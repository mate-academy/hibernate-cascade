package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create message by id " + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            Message message = session.get(Message.class, id);
            if (message != null) {
                return message;
            } else {
                throw new RuntimeException("Can't get message by id " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't get message by id " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
            Root<Message> root = criteriaQuery.from(Message.class);
            CriteriaQuery<Message> all = criteriaQuery.select(root);
            TypedQuery<Message> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get message list", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Message entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Can't remove message by id " + entity.getId(), e);
        }
    }
}
