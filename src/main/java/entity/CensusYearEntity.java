package entity;
import javax.persistence.*;

@Entity
@Table(name = "CENSUSYEAR", schema = "canadacensus", catalog = "")
public class CensusYearEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "censusYearID")
    private int censusYearId;
    @Basic
    @Column(name = "censusYear")
    private int censusYear;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CensusYearEntity that = (CensusYearEntity) o;

        if (censusYearId != that.censusYearId) return false;
        if (censusYear != that.censusYear) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = censusYearId;
        result = 31 * result + censusYear;

        return result;
    }
}
