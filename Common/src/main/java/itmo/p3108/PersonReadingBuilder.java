package itmo.p3108;

import itmo.p3108.adapter.LocalDateAdapter;
import itmo.p3108.model.Color;
import itmo.p3108.model.Coordinates;
import itmo.p3108.model.Country;
import itmo.p3108.model.Location;
import itmo.p3108.util.CheckData;
import itmo.p3108.util.UserReader;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;


public final class PersonReadingBuilder {
    private static final PersonReadingBuilder createPerson = new PersonReadingBuilder();
    @Getter
    private static Long id = 1L;
    private final CheckData checkData = new CheckData();

    private PersonReadingBuilder() {
    }

    public static void setId(Long id1) {
        if (id1 > 0) {
            id = id1 + 1;
        }
    }

    public static PersonReadingBuilder getInstance() {
        return createPerson;
    }

    public String properRead(String message) {
        return Objects.requireNonNull(UserReader.read(), message).trim();
    }

    public String createName() {
        String name = "";
        while (name.equals("")) {
            System.out.println("enter name");
            String test = properRead("name must not be null");
            if (!checkData.checkPersonName(test)) {
                continue;
            }
            name = test;
        }
        return name;
    }

    public Long createId() {
               int random1 =  (int) ((Math.random() * (1000 - 2)) + 2);
        return (id++)  + random1;
    }

    public double createHeight() {

        double height = -1;
        while (height < 0) {
            String test = "";

            System.out.println("enter height");
            test = properRead("height must not be null");

            if (!checkData.checkPersonHeight(test)) {
                continue;
            }
            height = Double.parseDouble(test);
        }
        return height;
    }

    public LocalDate createBirthDay() {
        String birthday = "";
        while (birthday.equals("")) {
            birthday = "";
            System.out.println("enter birthday in format MM-dd-yyyy");
            String test = properRead("birthday must not be null");

            if (!checkData.checkPersonBirthday(test)) {
                continue;
            }
            birthday = test;
        }
        return LocalDateAdapter.getInstance().unmarshal(birthday);
    }


    public Color createColor() {
        Color color = null;

        while (color == null) {

            System.out.println("choose colour");
            System.out.println("choose one number");
            System.out.println(Arrays.toString(Color.colors()));
            String test = properRead("Color must not be null");
            CheckData checkData = new CheckData();
            if (!checkData.checkPersonColourReadingConsole(test)) {
                continue;
            }
            Optional<Color> optionalColor = Color.newValue(test);
            if (optionalColor.isPresent()) {
                color = optionalColor.get();
            }

        }
        return color;
    }


    public Country createNationality() {
        Country nationality = null;

        while (nationality == null) {

            System.out.println("choose nationality");
            System.out.println("choose one  number");

            System.out.println(Arrays.toString(Country.countries()));
            String test = properRead("country must not be null");
            if (!checkData.checkPersonNationalityReadingFromConsole(test)) {
                continue;
            }
            Optional<Country> optionalCountry = Country.newValue(test);
            if (optionalCountry.isPresent()) {
                nationality = optionalCountry.get();

            }
        }
        return nationality;
    }


    public Coordinates createCoordinates() {
        String x = "";
        String y = "";

        while (x.equals("")) {

            System.out.println("enter coordinates ");

            System.out.println("enter x");

            String test = properRead("x must not be null");

            if (!checkData.checkCoordinatesX(test)) {
                continue;
            }
            x = test;
        }
        while (y.equals("")) {
            System.out.println("enter y");

            String test = properRead("y must not be null");
            if (!checkData.checkCoordinatesY(test)) {
                continue;
            }
            y = test;

        }
        return Coordinates.builder().coordinatesX(Integer.valueOf(x)).coordinatesY(Float.valueOf(y)).build();
    }


    public Location createLocation() {
        String x = "";
        String y = "";
        String z = "";
        String name = "";


        while (x.equals("")) {
            System.out.println("enter Location name and it's coordinates ");

            System.out.println("enter  x");

            String test = properRead("x must not be null");

            if (!checkData.checkLocationX(test)) {
                continue;
            }

            x = test;
        }
        while (y.equals("")) {

            System.out.println("enter  y");

            String test = properRead("y must not be null");

            if (!checkData.checkLocationX(test)) {
                continue;
            }
            y = test;
        }
        while (z.equals("")) {
            System.out.println("enter  z");

            String test = properRead("z must not be null");

            if (!checkData.checkLocationZ(test)) {
                continue;
            }
            z = test;
        }
        while (name.equals("")) {
            System.out.println("enter location name");
            String test = properRead("location must not be null");

            if (!checkData.checkPersonName(test)) {
                continue;
            }
            name = test;
        }
        return Location.builder().locationZ(Float.parseFloat(z)).locationY(Float.valueOf(y)).locationX(Double.parseDouble(x)).locationName(name).build();
    }
}
