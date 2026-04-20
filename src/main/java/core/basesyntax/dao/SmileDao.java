package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import core.basesyntax.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SmileDaoImpl implements SmileDao {

    @Override
    public Smile create(Smile smile) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(smile);
        tx.commit();
        session.close();
        return smile;
    }

    @Override
    public Smile get(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Smile smile = session.get(Smile.class, id);
        session.close();
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Smile> list = session.createQuery("from Smile", Smile.class).getResultList();
        session.close();
        return list;
    }
}

