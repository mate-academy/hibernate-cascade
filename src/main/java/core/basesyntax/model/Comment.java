package core.basesyntax.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@NamedQuery(name = "getAllComments", query = "SELECT c FROM Comment c")
@Table(name = "comments")
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

    public Comment() {
    }

    public Comment(String content, List<Smile> smiles) {
        this.content = content;
        this.smiles = smiles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Smile> getSmiles() {
        return smiles;
    }

    public void setSmiles(List<Smile> smiles) {
        this.smiles = smiles;
    }

    @Override
    public String toString() {
        return "Comment{"
               + "id=" + id
               + ", content='" + content + '\''
               + ", smiles=" + smiles
               + '}';
    }
}
