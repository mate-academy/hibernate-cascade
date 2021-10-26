package core.basesyntax.model;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

public class Message {
    private Long id;
    private String content;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
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
