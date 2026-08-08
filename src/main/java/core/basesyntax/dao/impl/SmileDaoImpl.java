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
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.persist(entity);

        tx.commit();
        session.close();
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Session session = factory.openSession();
        Smile smile = session.get(Smile.class, id);
        session.close();
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        Session session = factory.openSession();
        List<Smile> smiles = session
                .createQuery("FROM Smile", Smile.class)
                .getResultList();
        session.close();
        return smiles;
    }
}
