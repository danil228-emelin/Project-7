/*
import itmo.p3108.chain.HandlerOneArgument;
import itmo.p3108.command.CountByHeight;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.model.Person;
import itmo.p3108.util.CollectionController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HandlerOneArgumentTest {
    HandlerOneArgument handlerOneArgument;

    @BeforeEach
    void clear() {
        CollectionController.getInstance().getPersonList().clear();
    }

    @Test
    public void check_oneArgument_handler() {
        handlerOneArgument = new HandlerOneArgument();
        OneArgument<Double> command = new CountByHeight();
        command.setParameter(182.0);
        Person testPerson = Person.builder().personHeight(182.0).build();
        CollectionController.getInstance().getPersonList().add(testPerson);
        String result = handlerOneArgument.processRequest(command);
         Assertions
                .assertThat(result)
                .as("Server:OneArgumentHandler can't execute command")
                .isEqualTo("1");
    }
}
*/
