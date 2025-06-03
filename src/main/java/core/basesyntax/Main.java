package core.basesyntax;

import core.basesyntax.model.Comment;
import core.basesyntax.model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        User userAlex = new User();
        userAlex.setUsername("Alex");

        Comment comment1 = new Comment();
        comment1.setContent("That's funny");

        comment1.setUser(userAlex);

        userAlex.setComments(List.of(comment1));

    }
}
