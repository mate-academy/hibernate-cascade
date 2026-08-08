package core.basesyntax.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne; // ✅ Змінено на ManyToOne
import jakarta.persistence.Table;

@Entity
@Table(name = "smiles")
public class Smile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;

    // ✅ Змінено з ManyToMany: Smile тепер є залежною стороною у зв'язку ManyToOne.
    // FK (зовнішній ключ) буде знаходитися в таблиці smiles і посилатися на Comment.
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public Smile() {
    }

    public Smile(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Smile{"
                + "id=" + id
                + ", value='" + value + '\''
                + '}';
    }
}
