package core.basesyntax.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Comment {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter

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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    public void setSmiles(List<Smile> smiles) {
        this.smiles = smiles;
    }
}
