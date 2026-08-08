package core.basesyntax.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;

public class MessageDetailsDaoImpl implements MessageDetailsDao {
    private final SessionFactory sessionFactory;

    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public MessageDetailsDaoImpl() {
        this(HibernateUtil.getSessionFactory());
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
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
            throw new RuntimeException("Can't create message details", e);
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MessageDetails.class, id);
        }
    }
}


