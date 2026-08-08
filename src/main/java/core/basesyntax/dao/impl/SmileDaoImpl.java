package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not add a "
                    + Smile.class.getSimpleName() + " entity to DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Smile foundedEntity = session.find(Smile.class, id);
            transaction.commit();
            return foundedEntity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not get a " + Smile.class.getSimpleName()
                    + " to a DB. ", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            List<Smile> allFoundedEntities =
                    session.createQuery("FROM " + Smile.class.getName(), Smile.class)
                            .getResultList();

            transaction.commit();
            return allFoundedEntities;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can not get a " + Smile.class.getSimpleName()
                    + " to a DB. ", e);

        }
    }

}
