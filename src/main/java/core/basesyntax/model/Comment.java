package core.basesyntax.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import javax.persistence.*;

@Entity
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
    @OneToMany
    private List<Smile> smiles;
}
