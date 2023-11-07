package core.basesyntax.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;
    private final Class<T> entityClass;

    protected AbstractDao(SessionFactory sessionFactory, Class<T> entityClass) {
        this.factory = sessionFactory;
        this.entityClass = entityClass;
    }

    public T create(T entity) {
        Session session = factory.getCurrentSession();
        session.save(entity);
        return entity;
    }

    public T get(Long id) {
        Session session = factory.getCurrentSession();
        return session.get(entityClass, id);
    }

    public List<T> getAll() {
        Session session = factory.getCurrentSession();
        return session.createQuery("FROM " + entityClass.getName(), entityClass).list();
    }

    public void remove(T entity) {
        Session session = factory.getCurrentSession();
        session.remove(entity);
    }
}
