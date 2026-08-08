package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {

    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void remove(Comment comment) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            // 1. Приєднуємо коментар до поточної сесії
            Comment managedComment = session.merge(comment);

            // 2. ✅ Виправлення: Розриваємо двосторонній зв'язок із Smile.
            // Це гарантує, що поле comment_id у таблиці smiles стане NULL,
            // оскільки ми не використовували CascadeType.REMOVE.
            for (Smile smile : managedComment.getSmiles()) {
                smile.setComment(null);
                // Оновлюємо Smile, щоб зміни в полі comment були збережені
                session.merge(smile);
            }
            // Очищуємо колекцію у самому Comment
            managedComment.getSmiles().clear();

            // 3. Видаляємо коментар (Comment).
            // Оскільки ми розірвали зв'язок, це безпечно, і Smile залишаються в БД.
            session.remove(managedComment);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove Comment " + comment, e);
        }
    }
}
