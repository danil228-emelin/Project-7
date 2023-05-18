package itmo.p3108.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * class Coordinates using as coordinates for  @see {@link Person}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = 499988001L;

    private Integer coordinatesX;

    private Float coordinatesY;

    @Override
    public String toString() {
        return String.format("Coordinates{x=%d, y= %f}", coordinatesX, coordinatesY);
    }

}
