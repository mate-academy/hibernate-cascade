package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import exceptions.DataProcessingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends
        AbstractDao<MessageDetails> implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails get(Long id) {
        MessageDetails messageDetails;
        try (Session session = factory.openSession()) {
            messageDetails = session.get(MessageDetails.class, id);
            return messageDetails;
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving message details. ", e);
        }
    }
}
