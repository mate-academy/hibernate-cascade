package core.basesyntax.dao.impl;

import core.basesyntax.dao.GenericDao;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

public class GenericDaoImpl<T> extends AbstractDao implements GenericDao<T> {
    private final Class<T> entityClass;

    public GenericDaoImpl(SessionFactory sessionFactory, Class<T> entityClass) {
        super(sessionFactory);
        this.entityClass = entityClass;
    }

    @Override
    public T create(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save entity: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public T get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(entityClass, id);
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = factory.openSession()) {
            HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
            criteriaQuery.from(entityClass);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get entities from DB.", e);
        }
    }

    @Override
    public void remove(Object entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove entity: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
