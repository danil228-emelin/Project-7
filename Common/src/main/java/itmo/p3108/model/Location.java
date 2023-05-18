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
public class Location implements Serializable {
    @Serial
    private static final long serialVersionUID = 498788001L;

    private Double locationX;

    private Float locationY;

    private Float locationZ;

    private String locationName;

    @Override
    public String toString() {
        return String.format("Location{x=%f, y=%f, z=%f ,name=%s}", locationX, locationY, locationZ, locationName);
    }

}
