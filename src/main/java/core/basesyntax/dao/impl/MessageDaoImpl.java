package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao<Message> implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Message.class);
    }

    @Override
    public Message create(Message entity) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            MessageDetails messageDetails = entity.getMessageDetails();
            if (messageDetails != null) {
                session.save(messageDetails);
            }
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create entity ", e);
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        return super.get(id);
    }

    @Override
    public List<Message> getAll() {
        return super.getAll();
    }

    @Override
    public void remove(Message entity) {
        super.remove(entity);
    }
}
