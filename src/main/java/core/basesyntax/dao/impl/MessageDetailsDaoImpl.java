package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import jakarta.persistence.EntityManagerFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        return null;
    }

    @Override
    public MessageDetails get(Long id) {
        return null;
    }
}
