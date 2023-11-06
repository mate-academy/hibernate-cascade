package core.basesyntax.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import java.util.List;

public class User {
    private Long id;
    private String username;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Comment> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", comments=" + comments
                + '}';
    }
}
