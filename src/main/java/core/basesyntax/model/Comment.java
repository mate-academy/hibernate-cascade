package core.basesyntax.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @OneToMany(mappedBy = "comment", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Smile> smiles;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    public Comment(String content, List<Smile> smiles) {
        this.content = content;
        this.smiles = smiles != null ? smiles : new ArrayList<>();
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
        if (smiles == null) {
            smiles = new ArrayList<>();
        }
        return smiles;
    }

    public void setSmiles(List<Smile> smiles) {
        this.smiles = smiles;
    }

    @Override
    public String toString() {
        return "Comment{"
               + "id=" + id
               + ", content='" + content
               + '\''
               + ", smiles=" + smiles
               + '}';
    }
}
