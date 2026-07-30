package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {

    private final SessionFactory sessionFactory;

    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Saving Message Details to Database Error, cause: "
                    + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MessageDetails.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Loading Message Details from Database Error, cause: "
                    + e.getMessage(), e);
        }
    }
}
