package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();

            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't create new MessageDetails " + entity, e);
        }
    }

    @Override
    public MessageDetails get(Long id) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            MessageDetails messageDetails = session.find(MessageDetails.class, id);
            transaction.commit();

            return messageDetails;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't find MessageDetails by id: " + id, e);
        }
    }
}
