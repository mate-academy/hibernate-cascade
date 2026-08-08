package core.basesyntax.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private List<Comment> comments;

    // getters & setters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}



