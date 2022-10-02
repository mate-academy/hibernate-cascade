package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao<MessageDetails>
        implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(MessageDetails.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can not get message details from DB with id " + id);
        }
    }
}
