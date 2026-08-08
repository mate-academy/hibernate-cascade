package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SmileDao smileDao = null;
        Smile smile = smileDao.get(1L);

        Comment comment = new Comment();
        comment.setSmiles(List.of(smile));

        CommentDao commentDao = null;
        commentDao.create(comment);

    }
}
