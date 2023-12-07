package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import java.util.List;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao<MessageDetails>
        implements MessageDetailsDao {

    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, MessageDetails.class);
    }

    @Override
    public MessageDetails create(MessageDetails messageDetails) {
        save(messageDetails);
        return messageDetails;
    }

    @Override
    public MessageDetails get(Long id) {
        return findById(id);
    }

    @Override
    public List<MessageDetails> getAll() {
        return super.getAll(MessageDetails.class);
    }
}
