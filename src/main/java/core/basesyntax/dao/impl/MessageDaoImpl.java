package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        try (var session = factory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Message entity: " + entity, e);
        }
    }

    @Override
    public Message get(Long id) {
        try (var session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Message entity by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        final String query = "FROM Message"; // HQL query to fetch all Message entities
        try (var session = factory.openSession()) {
            return session.createQuery(query, Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all Message entities", e);
        }
    }

    @Override
    public void remove(Message entity) {
        try (var session = factory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove Message entity: " + entity, e);
        }
    }
}
