package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(entity);
                transaction.commit();
                return entity;
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't create Message", e);
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get Message by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Message e order by e.id",
                    Message.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all Message entities", e);
        }
    }

    @Override
    public void remove(Message entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Message managed = session.get(Message.class, entity.getId());
                if (managed != null) {
                    session.remove(managed);
                }
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't remove Message", e);
        }
    }
}
