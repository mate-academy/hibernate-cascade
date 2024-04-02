package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
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
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(Smile.class, id))
                    .orElseThrow(() -> new RuntimeException());
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createNativeQuery("select * from smiles", Smile.class).getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException();
        }
    }
}
