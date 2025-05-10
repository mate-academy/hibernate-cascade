package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
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
            throw new RuntimeException("Can't create entity to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Optional<Smile> smile;
        try (Session session = factory.openSession()) {
            smile = Optional.ofNullable(session.get(Smile.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can't get user from DB", e);
        }
        return smile.get();
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smiles = new ArrayList<>();
        Optional<Smile> smile;
        int i = 1;
        try (Session session = factory.openSession()) {
            smile = Optional.ofNullable(session.get(Smile.class, i));
            while (smile.isPresent()) {
                smile = Optional.ofNullable(session.get(Smile.class, i));
                smiles.add(smile.get());
                i++;
                smile = Optional.ofNullable(session.get(Smile.class, i));
            }
        } catch (Exception e) {
            throw new RuntimeException("Can't get user from DB", e);
        }
        return smiles;
    }
}
