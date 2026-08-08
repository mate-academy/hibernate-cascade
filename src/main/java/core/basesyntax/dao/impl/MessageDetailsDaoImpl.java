package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        factory.inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        return factory.fromSession(session -> session.get(MessageDetails.class, id));
    }
}
