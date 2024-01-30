package entity;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AGEGROUP", schema = "canadacensus", catalog = "")
public class AgeGroupEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ageGroupID")
    private int ageGroupId;
    @Basic
    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgeGroupEntity that = (AgeGroupEntity) o;

        if (ageGroupId != that.ageGroupId) return false;
        if (!Objects.equals(description, that.description)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ageGroupId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
