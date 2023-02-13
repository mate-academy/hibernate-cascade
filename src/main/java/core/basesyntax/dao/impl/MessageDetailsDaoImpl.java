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
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save message details in DB", e);
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        MessageDetails messageDetails;
        try (Session session = factory.openSession()) {
            messageDetails = session.get(MessageDetails.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message details with id=" + id + " from DB", e);
        }
        return messageDetails;
    }
}
