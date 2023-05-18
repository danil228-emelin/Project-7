package itmo.p3108.util;

import itmo.p3108.model.Color;
import itmo.p3108.model.Country;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CheckData {
    @SuppressWarnings("all")
    private final String CREATION_TIME_FORMAT = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z";
    @SuppressWarnings("all")
    private final String BIRTHDAY_FORMAT = "[0-9][1-9]-[0-9][1-9]-[1-9]\\d{3}";
    @SuppressWarnings("all")
    private final String INT_NUMBER_FORMAT = "-?\\d+";
    @SuppressWarnings("all")
    private final String POSITIVE_NUMBER_FORMAT = "\\d+";
    @SuppressWarnings("all")
    private final String FLOAT_NUMBER_FORMAT = "-?\\d+\\.?\\d*";
    @SuppressWarnings("all")
    private final String POSITIVE_FLOAT_NUMBER_FORMAT = "\\d+\\.?\\d*";
    @SuppressWarnings("all")
    private final String NAME_FORMAT = "(\\w+-?\\w*)";


    @Checking
    public boolean checkPersonNationalityNumber(String test) {

        if (!Country.isPresent(test)) {
            log.error("error:during nationality setting line has wrong format");
            System.err.println("error:during nationality setting line has wrong format");
            return false;
        }
        return true;
    }

    public boolean checkPersonColourReadingConsole(String test) {
        if (!test.matches("[1-5]")) {
            log.error("error:during colour setting line has wrong format");
            System.err.println("error:during colour setting line has wrong format");
            return false;

        }
        return true;
    }

    /**
     * @see Color
     */
    @Checking
    public boolean checkPersonEyeColorNumber(String test) {
        if (!Color.isPresent(test)) {
            log.error("error:during colour setting line has wrong format");
            System.err.println("error:during colour setting line has wrong format");
            return false;

        }
        return true;
    }

    @Checking
    public boolean checkPersonBirthday(String test) {
        if (!test.matches(BIRTHDAY_FORMAT)) {
            log.error("error:during birthday setting line has wrong format");
            System.err.println("error:during birthday setting line has wrong format");
            return false;
        }
        String[] strings = test.split("-");

        if (Integer.parseInt(strings[0]) > 12) {
            log.error("error:during birthday setting value is incorrect");

            System.err.println("error:during birthday setting value is incorrect\n month can't be greater than 12");
            return false;

        }

        if (Integer.parseInt(strings[1]) > 31) {
            log.error("error:during birthday setting value is incorrect");
            System.err.println("error:during birthday setting value is incorrect\n day can't be greater than 31");
            return false;

        }


        if (Integer.parseInt(strings[2]) > 2024 || Integer.parseInt(strings[2]) < 1920) {
            log.error("error:during birthday setting value is incorrect");
            System.err.println("error:during birthday setting value is incorrect \n year can't be greater than 2023 and less than 1920");
            return false;

        }

        return true;
    }

    public boolean checkPersonNationalityReadingFromConsole(String test) {
        if (!test.matches("[1-4]")) {
            log.error("error:during nationality setting line has wrong format");
            System.err.println("error:during nationality setting line has wrong format");
            return false;
        }
        return true;
    }

    @Checking
    public boolean checkCoordinatesX(String test) {
        if (!test.matches(INT_NUMBER_FORMAT)) {
            log.error("error:during coordinate x setting, wrong format");
            System.err.println("error:during coordinate x setting, wrong format \n x is integer");
            return false;

        }
        if (test.length() > 15) {
            log.error("error:during coordinate x setting,value is too large");
            System.err.println("error:during coordinate x setting,value is too large");
            return false;

        }
        return true;
    }

    @Checking
    public boolean checkCoordinatesY(String test) {
        if (!test.matches(FLOAT_NUMBER_FORMAT)) {
            log.error("error:during coordinate y setting");
            System.err.println("error:during coordinate y setting \n value is whole or fractional number");
            return false;

        }
        if (test.length() > 15) {
            log.error("error:during coordinate y setting value is too large");
            System.err.println("error:during coordinate y setting \n value is too large");
            return false;

        }
        return true;
    }

    @Checking
    public boolean checkLocationX(String test) {
        if (!test.matches(FLOAT_NUMBER_FORMAT)) {
            log.error("error:during location coordinate x setting,wrong format");
            System.err.println("error:during location coordinate x setting \n value is whole or fractional number");
            return false;

        }
        if (test.length() > 15) {
            log.error("error:during location coordinate x setting value is too large");
            System.err.println("error:during location coordinate x setting \n value is too large");
            return false;

        }
        return true;
    }

    @Checking
    public boolean checkLocationY(String test) {

        if (!test.matches(FLOAT_NUMBER_FORMAT)) {
            log.error("error:during location coordinate y setting wrong format");

            System.err.println("error:during location coordinate y setting \n value is whole or fractional number");
            return false;

        }
        if (test.length() > 15) {
            log.error("error:during location coordinate y setting value is too large");

            System.err.println("error:during location coordinate y setting \n value is too large");
            return false;

        }
        return true;
    }

    @Checking
    public boolean checkPersonId(String test) {
        if (!test.matches(POSITIVE_NUMBER_FORMAT)) {
            log.error("error:id has wrong format");
            return false;
        }

        return true;
    }

    @Checking
    public boolean checkLocationZ(String test) {

        if (!test.matches(FLOAT_NUMBER_FORMAT)) {
            log.error("error: error:during location coordinate z setting");
            System.err.println("error:during location coordinate z setting \n value is whole or fractional number");
            return false;

        }
        if (test.length() > 15) {
            log.error("error: error:during location coordinate z setting,value is too large");
            System.err.println("error:during location coordinate z setting \n value is too large");
            return false;

        }
        return true;
    }

    @Checking
    public boolean checkPersonHeight(String test) {
        if (!test.matches(POSITIVE_FLOAT_NUMBER_FORMAT)) {
            log.error("error:during height setting");
            System.err.println("error:during height setting \n value is positive whole or fractional number");
            return false;

        }
        if (test.length() > 15) {
            log.error("error:during height setting");
            System.err.println("error:during height setting value is too large");
            return false;

        }
        return true;
    }

    @Checking
    public boolean checkPersonCreationDate(String test) {

        if (!test.matches(CREATION_TIME_FORMAT)) {
            System.err.println("error:creation time has wrong format");
            return false;

        }
        return true;
    }

    @Checking
    public boolean checkLocationName(String test) {
        return checkPersonName(test);
    }

    @Checking
    public boolean checkPersonName(String test) {

        if (test.length() > 40) {
            log.error("error during name setting:too long line");
            System.err.println("error during name setting:too long line \n maximum is 40 letters");
            return false;

        }
        if (!test.matches(NAME_FORMAT)) {
            log.error("error during name setting:line has wrong format ");

            System.err.println("error during name setting:line has wrong format \n use only digits,letters,and dash for double name");
            return false;

        }
        return true;
    }


    public <T extends Annotation> boolean wrapperCheckArguments(@NonNull String[] collection, Class<T> annotationClass, @NonNull String[] argumentOrder) {

        PrintStream error = System.err;
        System.setErr(new PrintStream(OutputStream.nullOutputStream()));
        boolean result = checkArguments(collection, annotationClass, argumentOrder);
        System.setErr(error);
        return result;
    }


    private <T extends Annotation> boolean checkArguments(@NonNull String[] collection, Class<T> annotationClas, @NonNull String[] argumentOrder) {

        Optional<Set<Method>> set = Reflection.findAllMethodsWithAnnotation("itmo.p3108.util", annotationClas);
        if (set.isEmpty()) {
            log.error("validation methods are empty");
            return false;
        }
        if (collection.length != argumentOrder.length) {
            log.error(String.format("collection length and argument order length are not equal,%d and %d", collection.length, argumentOrder.length));
            return false;
        }

        for (int i = 0; i < collection.length; i++) {
            String data = collection[i];
            String dataName = argumentOrder[i];

            Optional<Method> checkMethod = set.get().stream().filter(x -> x.getName().toLowerCase().trim().contains(dataName.toLowerCase().trim())).findFirst();
            if (checkMethod.isEmpty()) {
                log.error(String.format("Can't find checkMethod for %s", dataName));
                return false;
            }
            try {
                Object result = checkMethod.get().invoke(this, data.trim());
                if (!(result instanceof Boolean resultChecking) || !resultChecking) {
                    return false;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error(e.getMessage());
                return false;
            }

        }
        return true;
    }


}
