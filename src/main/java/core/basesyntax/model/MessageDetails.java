package core.basesyntax.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class MessageDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageDetailsId;
    private String sender;
    private LocalDateTime sentTime;

    @OneToOne
    @JoinColumn(name = "message")
    private Message message;

    public MessageDetails() {
    }

    public Long getMessageDetailsId() {
        return messageDetailsId;
    }

    public void setMessageDetailsId(Long id) {
        this.messageDetailsId = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }
}
