package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    private static final String EXCEPTION_CAN_T_CREATE_DETAILS_MESSAGE =
            "Can not to create details for message with id: ";
    private static final String EXCEPTION_CAN_T_GET_DETAILS_MESSAGE =
            "Can not to get details for message with id: ";

    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
                return entity;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
            throw new RuntimeException(EXCEPTION_CAN_T_CREATE_DETAILS_MESSAGE + entity.getId());
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(MessageDetails.class, id);
        } catch (Exception e) {
            throw new RuntimeException(EXCEPTION_CAN_T_GET_DETAILS_MESSAGE + id);
        }
    }
}
