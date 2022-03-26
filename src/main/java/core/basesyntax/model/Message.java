package core.basesyntax.model;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

public class Message {
    private Long id;
    private String content;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private MessageDetails messageDetails;

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

    public MessageDetails getMessageDetails() {
        return messageDetails;
    }

    public void setMessageDetails(MessageDetails messageDetails) {
        this.messageDetails = messageDetails;
    }
}
