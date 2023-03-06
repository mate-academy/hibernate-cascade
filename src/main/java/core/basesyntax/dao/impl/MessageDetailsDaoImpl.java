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
        Session session = super.factory.openSession();
        MessageDetails messageDetails = session.get(MessageDetails.class, id);
        session.close();
        return messageDetails;
    }
}
