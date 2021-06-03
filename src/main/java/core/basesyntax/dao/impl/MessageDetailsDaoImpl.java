package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import java.util.Optional;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save message details" + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(MessageDetails.class, id)).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("There is no message details with id " + id, e);
        }
    }
}
