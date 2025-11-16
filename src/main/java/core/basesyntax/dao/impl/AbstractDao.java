package core.basesyntax.dao.impl;

import core.basesyntax.dao.DataProcessingException;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected <T> T findById(Class<T> entityType, Long id) {
        try (Session session = factory.openSession()) {
            return session.find(entityType, id);
        } catch (Exception e) {
            throw new DataProcessingException("Cannot find id: " + id, e);
        }
    }

    protected <E> List<E> getEntities(Class<E> entityType) {
        String hql = "SELECT a FROM " + entityType.getTypeName() + " a";
        try (Session session = factory.openSession()) {
            return session.createQuery(hql, entityType).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Cannot get entities of type: " + entityType, e);
        }
    }

    private void handleSessionRequest(Object entity, Consumer<Session> sessionConsumer)
            throws IOException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            sessionConsumer.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new IOException("Cannot handle session request for: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    protected void removeEntity(Object entity) {
        try {
            handleSessionRequest(entity, session -> session.remove(entity));
        } catch (IOException e) {
            throw new DataProcessingException("Cannot remove: " + entity, e);
        }
    }

    protected <T> T persistEntity(T entity) {
        try {
            handleSessionRequest(entity, session -> session.persist(entity));
            return entity;
        } catch (IOException e) {
            throw new DataProcessingException("Cannot persist: " + entity, e);
        }
    }
}
