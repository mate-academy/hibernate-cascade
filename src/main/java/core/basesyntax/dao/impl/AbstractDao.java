package core.basesyntax.dao.impl;

import core.basesyntax.dao.GenericDao;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public abstract class AbstractDao<T> implements GenericDao<T> {
    protected final SessionFactory factory;
    protected final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;

        this.entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T create(T entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            System.err.println("--- ПОБАЧЕНО ПЕРВИННИЙ ВИНЯТОК: "
                    + e.getClass().getName() + " ---");
            e.printStackTrace();
            System.err.println("------------------------------"
                    + "------------------------------------");

            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create entity: "
                    + entityClass.getSimpleName(), e);
        }
    }

    @Override
    public T get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(entityClass, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get entity by id: " + id, e);
        }
    }

    @Override
    public List<T> getAll() {
        try (Session session = factory.openSession()) {
            Query<T> query = session.createQuery("from "
                    + entityClass.getSimpleName(), entityClass);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all entities: "
                    + entityClass.getSimpleName(), e);
        }
    }
}
