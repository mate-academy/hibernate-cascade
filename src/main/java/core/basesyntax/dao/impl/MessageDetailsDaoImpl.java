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
        try {
            return factory.fromTransaction(session -> {
                session.persist(entity);
                return entity;
            });
        } catch (Exception e) {
            throw new RuntimeException("Can't create MessageDetails: " + entity, e);
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try {
            return factory.fromSession(session -> session.get(MessageDetails.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can't get MessageDetails by id: " + id, e);
        }
    }
}
