package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import java.util.List;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void create(MessageDetails entity) {

    }

    @Override
    public MessageDetails get(Long id) {
        return null;
    }

    @Override
    public List<MessageDetails> getAll() {
        return null;
    }

    @Override
    public void remove(MessageDetails entity) {

    }
}
