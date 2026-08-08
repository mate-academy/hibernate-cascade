package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void remove(User user) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            // 1. Приєднуємо користувача до поточної сесії
            User managedUser = session.merge(user);

            // 2. ✅ Головне виправлення: Розриваємо зв'язок із коментарями.
            // Це гарантує, що поле user_id у таблиці comments стане NULL (ON DELETE SET NULL).
            for (Comment comment : managedUser.getComments()) {
                comment.setUser(null);
            }
            // Очищуємо колекцію у самому User, щоб уникнути помилок під час видалення
            managedUser.getComments().clear();

            // 3. Видаляємо користувача
            session.remove(managedUser);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove User " + user, e);
        }
    }
}
