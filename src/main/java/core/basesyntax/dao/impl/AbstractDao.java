package core.basesyntax.dao.impl;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao<T> {
    protected final SessionFactory factory;

    protected AbstractDao(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    protected T createEntity(T entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add entity " + entity.getClass(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    protected List<T> getAll(Class<T> clazz) {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM "
                    + clazz.getName() + " a", clazz).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get data from DB");
        }
    }

    protected Optional<T> get(Long id, Class<T> clazz) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(clazz, id));
        } catch (Exception e) {
            throw new RuntimeException("Can't get an entity "
                    + clazz.getName() + " by id: " + id, e);
        }
    }

    protected T removeEntity(T entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove entity " + entity.toString());
        }
        return entity;
    }
}
