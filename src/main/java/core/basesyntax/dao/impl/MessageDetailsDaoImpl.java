package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        Session session = null;
        Transaction tr = null;
        try {
            session = factory.openSession();
            tr = session.beginTransaction();
            session.persist(entity);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            throw new RuntimeException("Cant create message detail");
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(MessageDetails.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cant get message details");
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
