package itmo.p3108.model;


import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * class Country using to describe current location of  @see {@link Person}
 */
@Slf4j
public enum Country implements Serializable {
    RUSSIA("1)russia"), FRANCE("2)france"), SPAIN("3)spain"), NORTH_KOREA("4)north_korea");
    @Serial
    private static final long serialVersionUID = 498988001L;
    private final String name;

    Country(String s) {
        name = s;
    }

    public static Optional<Integer> countyNumber(Country necessaryCountry) {
        for (Country country : Country.values()) {
            if (country.name.equals(necessaryCountry.name))
                try {
                    return Optional.of(Integer.parseInt(String.valueOf(necessaryCountry.name.charAt(0))));

                } catch (NumberFormatException numberFormatException) {
                    log.error(numberFormatException.toString());
                }
        }
        return Optional.empty();
    }

    public static Optional<Country> newValue(String str) {

        for (Country country : Country.values()) {
            if (country.getName().startsWith(str)) {
                return Optional.of(country);
            }
        }
        return Optional.empty();
    }

    /**
     * @return all constants converted to String
     */
    public static String[] countries() {

        return Arrays.stream(Country.values()).map(Country::getName).toArray(String[]::new);
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
        for (Country country : Country.values()) {
            if (country.getName().contains(test)) {
                return true;
            }

        }
        return false;
    }

    public String getName() {
        return name;
    }


}
