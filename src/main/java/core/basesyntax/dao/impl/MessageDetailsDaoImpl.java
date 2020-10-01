package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Log4j
public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails messageDetails) {
        log.info("Calling a create() method of MessageDetailsDaoImpl class");
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(messageDetails);
            log.info("Attempt to store messageDetails " + messageDetails + " to db.");
            transaction.commit();
            return messageDetails;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create messageDetails entity. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MessageDetails get(Long id) {
        log.info("Calling a get() method of MessageDetailsDaoImpl class");
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            MessageDetails messageDetails =
                    (MessageDetails) session.get(MessageDetails.class, id);
            log.info("Attempt to retrieve messageDetails " + messageDetails + " from db.");
            session.flush();
            return messageDetails;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert user entity. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
