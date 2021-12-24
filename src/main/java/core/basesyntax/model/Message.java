package core.basesyntax.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @OneToMany(cascade = {CascadeType.REMOVE,
            CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinTable(name = "messages_details",
    joinColumns = @JoinColumn(name = "messages_id"),
    inverseJoinColumns = @JoinColumn(name = "details_id"))
    private List<MessageDetails> messageDetails;

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

    public List<MessageDetails> getMessageDetails() {
        return messageDetails;
    }

    public void setMessageDetails(List<MessageDetails> messageDetails) {
        this.messageDetails = messageDetails;
    }
}
