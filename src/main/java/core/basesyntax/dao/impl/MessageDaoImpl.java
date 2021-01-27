package core.basesyntax.dao.impl;

import core.basesyntax.model.Message;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao<Message> {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
