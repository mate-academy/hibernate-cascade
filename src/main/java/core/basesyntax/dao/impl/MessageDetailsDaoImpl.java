package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import java.util.Optional;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        return (MessageDetails) super.createEntity(entity);
    }

    @Override
    public MessageDetails get(Long id) {
        Optional<MessageDetails> details = super.get(id, MessageDetails.class);
        return details.orElse(null);
    }
}
