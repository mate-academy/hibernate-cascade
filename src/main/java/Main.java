import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import java.util.List;
import org.hibernate.SessionFactory;

class Main {
    public static void main(String[] args) {
        final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        //1. Comment - Smile
        //commentSmileTest(sessionFactory);

        //2. User - Comment
        userCommentTest(sessionFactory);
    }

    private static void commentSmileTest(SessionFactory sessionFactory) {
        final CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        final SmileDao smileDao = new SmileDaoImpl(sessionFactory);

        Smile smile = new Smile(";-)");
        Smile smile1 = new Smile("8-]");
        smileDao.create(smile);
        smileDao.create(smile1);

        Comment comment = new Comment();
        comment.setContent("It's cool!");
        comment.setSmiles(List.of(smile, smile1));
        commentDao.create(comment);

        List<Comment> comments = commentDao.getAll();
        comments.forEach(System.out::println);
        commentDao.remove(comments.get(0));
    }

    private static void userCommentTest(SessionFactory sessionFactory) {
        final CommentDao commentDao = new CommentDaoImpl(sessionFactory);
        final SmileDao smileDao = new SmileDaoImpl(sessionFactory);

        Smile smile = new Smile(";-)");
        Smile smile1 = new Smile("8-]");
        smileDao.create(smile);
        smileDao.create(smile1);

        Comment comment = new Comment();
        comment.setContent("It's cool!");
        comment.setSmiles(List.of(smile, smile1));
        //commentDao.create(comment);

        User user = new User();
        user.setUsername("User 01");
        user.setComments(List.of(comment));
        UserDao userDao = new UserDaoImpl(sessionFactory);
        userDao.create(user);
        List<User> users = userDao.getAll();
        users.forEach(System.out::println);
        userDao.remove(users.get(0));
    }

}
