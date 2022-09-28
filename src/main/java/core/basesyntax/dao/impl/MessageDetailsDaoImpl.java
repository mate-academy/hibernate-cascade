package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    @Override
    public MessageDetails create(MessageDetails entity) {
        Session session = null;
        try {
            session = factory.openSession();
            session.save(entity);
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Can't create new message's detail: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            return session.find(MessageDetails.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message's detail by ID: " + id, e);
        }
    }
}
