package core.basesyntax.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "messages")
public class Message implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @OneToOne
    @Cascade(value = {CascadeType.PERSIST, CascadeType.DELETE})
    @JoinColumn(name = "details_id")
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

    @Override
    public Message clone() {
        try {
            Message message = (Message) super.clone();
            if (messageDetails != null) {
                message.setMessageDetails(messageDetails.clone());
            }
            return message;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Can't make clone of " + this, e);
        }
    }

    @Override
    public String toString() {
        return "Message{"
                + "id=" + id
                + ", content='" + content + '\''
                + ", messageDetails=" + messageDetails
                + '}';
    }
}
