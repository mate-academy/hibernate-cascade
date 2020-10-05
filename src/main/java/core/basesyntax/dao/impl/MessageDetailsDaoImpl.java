package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao<MessageDetails>
        implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            MessageDetails details = session.get(MessageDetails.class, id);
            transaction.commit();
            return details;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get the message details with ID "
                    + id + " from the DB.", e);
        }
    }
}
