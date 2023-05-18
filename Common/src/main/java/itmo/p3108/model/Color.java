package itmo.p3108.model;


import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * class Colour using as  @see {@link Person} eye's color
 */
@Slf4j
public enum Color implements Serializable {

    GREEN("1)green"),
    BLUE("2)blue"),
    YELLOW("3)yellow"),
    WHITE("4)white"),
    BROWN("5)brown");
    @Serial
    private static final long serialVersionUID = 489988001L;
    private final String name;

    Color(String name) {
        this.name = name;
    }

    public static Optional<Integer> colorNumber(Color necessaryColor) {
        for (Color color : Color.values()) {
            if (color.name.equals(necessaryColor.name))
                try {
                    return Optional.of(Integer.parseInt(String.valueOf(color.name.charAt(0))));

                } catch (NumberFormatException numberFormatException) {
                    log.error(numberFormatException.toString());
                }
        }
        return Optional.empty();
    }

    public static Optional<Color> newValue(String str) {
        for (Color color : Color.values()) {
            if (color.getName().startsWith(str))
                return Optional.of(color);
        }
        return Optional.empty();

    }

    /**
     * @param test by id check whether enum constant exist or not
     */
    public static boolean isPresent(String test) {
        try {
            Integer.parseInt(test);

        } catch (NumberFormatException exception) {
            log.error("test is not a digit");
            return false;
        }
        for (Color color : Color.values()) {
            if (color.getName().contains(test)) {

                return true;
            }

        }
        return false;
    }

    /**
     * @return all constants converted to String
     */
    public static String[] colors() {
        return Arrays.stream(Color.values()).map(Color::getName).toArray(String[]::new);
    }

    public String getName() {
        return name;
    }
}
