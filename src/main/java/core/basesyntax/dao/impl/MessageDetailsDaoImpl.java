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
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't create smile");
            }
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        Transaction transaction = null;
        Session session = factory.openSession();
        MessageDetails messageDetails = null;
        try {
            transaction = session.getTransaction();
            transaction.begin();
            messageDetails = session.get(MessageDetails.class, id);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't get comment by id");
            }
        } finally {
            session.close();
        }
        return messageDetails;
    }
}
