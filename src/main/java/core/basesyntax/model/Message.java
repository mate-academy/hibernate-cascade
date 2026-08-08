package core.basesyntax.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    // ✅ OneToOne зв'язок з MessageDetails.
    // mappedBy = "message" вказує, що MessageDetails відповідає за колонку зв'язку.
    // CascadeType.ALL, щоб MessageDetails зберігався/видалявся разом з Message.
    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
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
        // 💡 Додатково: Встановлюємо двосторонній зв'язок
        if (messageDetails != null) {
            messageDetails.setMessage(this);
        }
    }
}
