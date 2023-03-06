package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao<Message> implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message get(Long id) {
        Session session = super.factory.openSession();
        Message message = session.get(Message.class, id);
        session.close();
        return message;
    }

    @Override
    public List<Message> getAll() {
        Session session = super.factory.openSession();
        return session.createQuery("from Message", Message.class).getResultList();
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't remove message: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
