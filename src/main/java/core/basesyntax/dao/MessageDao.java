package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import core.basesyntax.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MessageDaoImpl implements MessageDao {

    @Override
    public Message create(Message entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
        return entity;
    }

    @Override
    public Message get(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Message message = session.get(Message.class, id);
        session.close();
        return message;
    }

    @Override
    public List<Message> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Message> list = session.createQuery("from Message", Message.class).getResultList();
        session.close();
        return list;
    }

    @Override
    public void remove(Message entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.remove(entity);
        tx.commit();
        session.close();
    }
}

