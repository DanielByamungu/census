package entity;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TOTALINCOME", schema = "canadacensus", catalog = "")
@NamedQuery(name="findAllIncome", query = "SELECT i FROM TotalIncomeEntity i")
public class TotalIncomeEntity {
    @Override
    public String toString() {
        return  "\nid = " + id +
                ", description = '" + description + '\'';
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private short id;
    @Basic
    @Column(name = "description")
    private String description;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalIncomeEntity that = (TotalIncomeEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(description, that.description)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (description != null ? description.hashCode() : 0);

        return result;
    }
}
