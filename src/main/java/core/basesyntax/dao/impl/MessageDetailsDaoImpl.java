package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    private  Session session = null;
    private Transaction transaction = null;

    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        try {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        transaction.begin();
        session.save(entity);
        transaction.commit();
    } catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        throw new RuntimeException("Can not save entity... :",e);
    } finally {
        if (session != null) {
            session.close();
        }
    }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        MessageDetails messageDetails = null;
        try (Session session1 = HibernateUtil.getSessionFactory().openSession()) {
            messageDetails = session1.get(MessageDetails.class, id);
        }
        return messageDetails;
    }
}
