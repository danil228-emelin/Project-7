package itmo.p3108.command;

import itmo.p3108.PersonReadingBuilder;
import itmo.p3108.model.Person;
import itmo.p3108.util.Users;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreatePerson {
    private static final PersonReadingBuilder personReadingBuilder = PersonReadingBuilder.getInstance();

    public static Person createPerson() {
        Person
                person = Person
                .builder()
                .personName(personReadingBuilder.createName())
                .personId(personReadingBuilder.createId())
                .personHeight(personReadingBuilder.createHeight())
                .personEyeColor(personReadingBuilder.createColor())
                .personNationality(personReadingBuilder.createNationality())
                .personBirthday(personReadingBuilder.createBirthDay())
                .coordinates(personReadingBuilder.createCoordinates())
                .location(personReadingBuilder.createLocation())
                .token(Users.getUser().getToken())
                .build();
        person.setPersonCreationDate(ZonedDateTime.now());
        return person;
    }
}
