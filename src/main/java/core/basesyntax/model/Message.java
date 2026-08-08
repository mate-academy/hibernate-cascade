package core.basesyntax.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    private MessageDetails messageDetails;

    // getters & setters
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public MessageDetails getMessageDetails() {
        return messageDetails;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMessageDetails(MessageDetails messageDetails) {
        this.messageDetails = messageDetails;
    }
}



