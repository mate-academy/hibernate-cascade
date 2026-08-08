package core.basesyntax.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToMany
    @JoinTable(name = "comments_smiles",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "smile_id"))
    private List<Smile> smiles;

    // getters & setters
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<Smile> getSmiles() {
        return smiles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSmiles(List<Smile> smiles) {
        this.smiles = smiles;
    }
}



