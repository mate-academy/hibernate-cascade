package core.basesyntax.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "massages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
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

    @Override
    public String toString() {
        return "Message{"
                + "id="
                + id
                + ", content='"
                + content
                + '\''
                + ", messageDetails="
                + messageDetails
                + '}';
    }
}
