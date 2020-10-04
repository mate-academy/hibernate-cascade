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
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<MessageDetails> messageDetails;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public List<MessageDetails> getMessageDetails() {
//        return messageDetails;
//    }
//
//    public void setMessageDetails(List<MessageDetails> messageDetails) {
//        this.messageDetails = messageDetails;
//    }
}
