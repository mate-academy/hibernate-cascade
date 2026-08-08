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
        try (var session = factory.openSession()) {
            var transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try (var session = factory.openSession()) {
            return session.get(MessageDetails.class, id);
        }
    }
}
