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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (RuntimeException cause) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete MessageDetails: " + entity, cause);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MessageDetails get(Long id) {
        Session session = factory.openSession();
        return session.get(MessageDetails.class, id);
    }
}
