package core.basesyntax;

import core.basesyntax.lib.Injector;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import core.basesyntax.service.CommentService;
import core.basesyntax.service.MessageService;
import core.basesyntax.service.SmileService;
import core.basesyntax.service.UserService;
import core.basesyntax.util.HibernateUtil;
import java.util.List;
import org.hibernate.SessionFactory;

public class Main {
    private static final Injector injector = Injector.getInstance("core.basesyntax");

    public static void main(String[] args) {
        final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        final CommentService commentService = (CommentService) injector
                .getInstance(CommentService.class);
        final MessageService messageService = (MessageService) injector
                .getInstance(MessageService.class);
        final SmileService smileService = (SmileService) injector.getInstance(SmileService.class);
        final UserService userService = (UserService) injector.getInstance(UserService.class);

        Smile smile1 = smileService.get(1L);
        Smile smile2 = smileService.get(2L);
        if (smile1 == null) {
            smile1 = smileService.create(new Smile("😊"));
        }
        if (smile2 == null) {
            smile2 = smileService.create(new Smile("😂"));
        }

        final Comment comment = new Comment();
        comment.setContent("Great post!");
        comment.setSmiles(List.of(smile1, smile2));
        final Comment createdComment = commentService.create(comment);
        System.out.println("Created Comment: " + createdComment);

        final User user = new User();
        user.setUsername("Olha");
        user.setComments(List.of(createdComment));
        final User createdUser = userService.create(user);
        System.out.println("Created User: " + createdUser);

        final MessageDetails details = new MessageDetails();
        details.setSender("Admin");
        details.setSentTime(java.time.LocalDateTime.now());

        final Message message = new Message();
        message.setContent("Hello, world!");
        message.setMessageDetails(details);
        final Message createdMessage = messageService.create(message);
        System.out.println("Created Message: " + createdMessage);

        userService.remove(createdUser);
        System.out.println("User removed, comments should remain.");

        messageService.remove(createdMessage);
        System.out.println("Message removed, details should be deleted too.");

        final List<Comment> remainingComments = commentService.getAll();
        System.out.println("Remaining comments: " + remainingComments);
    }
}
