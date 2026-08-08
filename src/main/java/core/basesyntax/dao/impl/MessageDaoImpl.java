package core.basesyntax.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;

public class MessageDaoImpl implements MessageDao {
    private final SessionFactory sessionFactory;

    public MessageDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public MessageDaoImpl() {
        this(HibernateUtil.getSessionFactory());
    }

    @Override
    public Message create(Message entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't create message", e);
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Message.class, id);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Message", Message.class).getResultList();
        }
    }

    @Override
    public void remove(Message entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.remove(session.contains(entity) ? entity : session.merge(entity));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't remove message", e);
        }
    }
}


