package entity;
import javax.persistence.*;

@Entity
@Table(name = "AGE", schema = "canadacensus", catalog = "")
public class AgeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ageID")
    private int ageId;
    @Basic
    @Column(name = "ageGroup")
    private int ageGroup;
    @Basic
    @Column(name = "censusYear")
    private int censusYear;
    @Basic
    @Column(name = "geographicArea")
    private int geographicArea;
    @Basic
    @Column(name = "combined")
    private int combined;
    @Basic
    @Column(name = "male")
    private int male;
    @Basic
    @Column(name = "female")
    private int female;

    public int getCombined() {
        return combined;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgeEntity ageEntity = (AgeEntity) o;

        if (ageId != ageEntity.ageId) return false;
        if (ageGroup != ageEntity.ageGroup) return false;
        if (censusYear != ageEntity.censusYear) return false;
        if (geographicArea != ageEntity.geographicArea) return false;
        if (combined != ageEntity.combined) return false;
        if (male != ageEntity.male) return false;
        if (female != ageEntity.female) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ageId;
        result = 31 * result + ageGroup;
        result = 31 * result + censusYear;
        result = 31 * result + geographicArea;
        result = 31 * result + combined;
        result = 31 * result + male;
        result = 31 * result + female;

        return result;
    }
}
