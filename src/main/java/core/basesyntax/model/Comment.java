package core.basesyntax.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "comment_smile",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "smile_id"))
    private List<Smile> smiles = new ArrayList<>();

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

    public void addSmile(Smile s) {
        smiles.add(s);
    }

    public void removeSmile(Smile s) {
        smiles.remove(s);
    }

}
//todo:     1. CommentDaoImpl All tests failed
// core.basesyntax.model.Comment.user' targets an unknown entity
// named 'core.basesyntax.model.User'
//          2. MessageDaoImpl 2 tests failed - create and remove
// java.lang.RuntimeException:
// Can't insert entity Message core.basesyntax.model.Message@3c28e5b6
//          3. UserDaoImpl 2 failed - getById with comments and remove with comments
//          4. MessageDetailsDaoImpl -
// org.hibernate.MappingException: An association from the table 'MessageDetails'
// refers to an unmapped class 'core.basesyntax.model.Message'
// +SmileDaoImpl - All OK
