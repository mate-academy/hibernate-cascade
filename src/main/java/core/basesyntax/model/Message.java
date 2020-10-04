package core.basesyntax.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Message {
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
    @OneToMany(cascade = CascadeType.ALL)
    private List<MessageDetails> messageDetails;
}
