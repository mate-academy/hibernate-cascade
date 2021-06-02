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
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t save Message Details " + entity);
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            MessageDetails messageDetails = null;
            messageDetails = session.get(MessageDetails.class, id);
            return messageDetails;
        } catch (Exception e) {
            throw new RuntimeException("Can`t get Message Details by id " + id);
        }
    }
}
