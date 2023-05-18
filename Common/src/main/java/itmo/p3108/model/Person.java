package itmo.p3108.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 498787001L;
    private Long personId;
    private String personName;

    private Coordinates coordinates;

    private java.time.ZonedDateTime personCreationDate;

    private Double personHeight;

    private java.time.LocalDate personBirthday;

    private Color personEyeColor;

    private Country personNationality;
    private Location location;
    private String token;

    @Override
    public int hashCode() {
        int result = personId.hashCode();
        result = 31 * result + personName.hashCode();
        result = 31 * result + coordinates.hashCode();
        result = 31 * result + personCreationDate.hashCode();
        result = 31 * result + personHeight.hashCode();
        result = 31 * result + personBirthday.hashCode();
        result = 31 * result + personEyeColor.hashCode();
        result = 31 * result + personNationality.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", personName='" + personName + '\'' +
                ", coordinates=" + coordinates +
                ", personCreationDate=" + personCreationDate +
                ", personHeight=" + personHeight +
                ", personBirthday=" + personBirthday +
                ", personEyeColor=" + personEyeColor +
                ", personNationality=" + personNationality +
                ", location=" + location +
                '}';
    }
}
