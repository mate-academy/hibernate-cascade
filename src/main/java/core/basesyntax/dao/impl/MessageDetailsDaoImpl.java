package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.MessageDetails;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails messageDetails) {
        return executeTransaction(session -> {
            session.persist(messageDetails);
            return messageDetails;
        });
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(MessageDetails.class, id))
                    .orElseThrow(() -> new DataProcessingException(
                            "MessageDetails not found with id " + id, new Throwable()));
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't retrieve MessageDetails by id " + id, e);
        }
    }
}
