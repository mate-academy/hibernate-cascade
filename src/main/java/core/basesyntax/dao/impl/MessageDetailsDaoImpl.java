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
    public MessageDetails create(MessageDetails details) {
        Transaction tx = null;

        try (Session session = factory
                .openSession()) {
            tx = session.beginTransaction();
            session.persist(details);
            tx.commit();
            return details;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory
                .openSession()) {
            return session.get(MessageDetails.class, id);
        }
    }
}
