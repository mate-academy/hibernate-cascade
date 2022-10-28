package core.basesyntax.model;

import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @OneToMany(cascade = {CascadeType.MERGE},
            fetch = FetchType.EAGER)
    private List<Smile> smiles;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(content, comment.content)
                && Objects.equals(smiles, comment.smiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, smiles);
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
