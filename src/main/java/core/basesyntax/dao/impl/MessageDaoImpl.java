package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends GenericDaoImpl<Message> implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Message.class, "SELECT m FROM Message m");
    }
}
