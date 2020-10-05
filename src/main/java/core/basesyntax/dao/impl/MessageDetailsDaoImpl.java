package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class MessageDetailsDaoImpl extends AbstractDao<MessageDetails>
        implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(MessageDetails.class, id);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to get the message details by id "
                    + id, exception);
        }
    }

    @Override
    public List<MessageDetails> getAll() {
        try (Session session = factory.openSession()) {
            Query<MessageDetails> getAllMessageDetails =
                    session.createQuery("from MessageDetails", MessageDetails.class);
            return getAllMessageDetails.getResultList();
        } catch (Exception exception) {
            throw new RuntimeException("Failed to get all messages' details", exception);
        }
    }
}
