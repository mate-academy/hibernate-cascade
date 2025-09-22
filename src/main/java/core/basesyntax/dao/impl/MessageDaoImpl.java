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
        Transaction tx;
        try (Session session = super.factory.openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message get(Long id) {
        return super.factory.openSession().get(Message.class, id);
    }

    @Override
    public List<Message> getAll() {
        try (Session session = super.factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Message> cq = cb.createQuery(Message.class);
            Root<Message> message = cq.from(Message.class);
            CriteriaQuery<Message> all = cq.select(message);
            TypedQuery<Message> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Message entity) {
        Transaction tx = null;
        try (Session session = super.factory.openSession()) {
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException(e);
        }

    }
}
