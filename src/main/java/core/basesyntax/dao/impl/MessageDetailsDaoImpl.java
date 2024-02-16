package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
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
            throw new RuntimeException("Can't create message details '" + entity + "' in DB");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        MessageDetails messageDetails;
        SessionFactory sessionFactory;
        Session session = null;
        try {
            sessionFactory = factory;
            session = sessionFactory.openSession();
            messageDetails = session.get(MessageDetails.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message details from DB by id " + id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return messageDetails;
    }
}
