package core.basesyntax.dao.impl;

import static core.basesyntax.HibernateUtil.getSessionFactory;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Can not create Message: " + entity, e);
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can not get Message by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = getSessionFactory().openSession()) {
            String sql = "FROM Message";
            return session.createQuery(sql, Message.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can not get all Message", e);
        }
    }

    @Override
    public void remove(Message entity) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            Message managedMessage = session.get(Message.class, entity.getId());
            if (managedMessage != null) {
                session.delete(managedMessage);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Can not remove Massage: " + entity, e);
        }
    }
}
