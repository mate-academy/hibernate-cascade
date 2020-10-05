package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import java.util.List;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao<MessageDetails>
        implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails item) {
        return super.create(item);
    }

    @Override
    public List<MessageDetails> getAll(Class<MessageDetails> clazz) {
        return super.getAll(clazz);
    }

    @Override
    public void remove(MessageDetails item) {
        super.remove(item);
    }

    @Override
    public MessageDetails get(Long id) {
        return super.get(id, MessageDetails.class);
    }
}
