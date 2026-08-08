package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    private Session getSession() {
        return factory.openSession();
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        getSession().save(entity);
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        return getSession().get(MessageDetails.class, id);
    }
}
