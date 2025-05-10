import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.User;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("Bob");

        Comment comment1 = new Comment();
        comment1.setContent("message1");

        Comment comment2 = new Comment();
        comment2.setContent("message2");

        user.setComments(Arrays.asList(comment1, comment2));

        UserDaoImpl userDao = new UserDaoImpl(HibernateUtil.getSessionFactory());

        userDao.create(user);

        List<User> users = userDao.getAll();
        if (users.isEmpty()) {
            System.out.println("Users table is empty");
        } else {
            System.out.println("Users table is not empty");
        }

        CommentDaoImpl commentDao = new CommentDaoImpl(HibernateUtil.getSessionFactory());
        List<Comment> comments = commentDao.getAll();
        if (!comments.isEmpty()) {
            System.out.println("Comments table is not empty");
        } else {
            System.out.println("Comments table is empty");
        }
        System.out.println("Manual check for user_comment table required.");

    }
}
