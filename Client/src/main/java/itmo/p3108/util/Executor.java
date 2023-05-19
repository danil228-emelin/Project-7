package itmo.p3108.util;

import itmo.p3108.command.Exit;
import itmo.p3108.command.LogIn;
import itmo.p3108.command.SignIn;
import itmo.p3108.command.type.Command;
import itmo.p3108.exception.AuthorizeException;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Executor process request from client.
 * It handles all authorization issues.
 * It analyzes input from client.
 */
@Slf4j
public class Executor {
    private final ServerChanel serverChanel = new ServerChanel(4445);
    private final Invoker invoker = Invoker.getInstance();


    private void serializeAndSend(Optional<Command> command, Consumer<Boolean> consumer) {

        command.ifPresentOrElse(
                command1 -> {
                    boolean serializedObject = SerializeObject.serialize(command1, serverChanel.getClientPort());
                    consumer.accept(serializedObject);
                }, () -> {
                    Optional<String> reply = serverChanel.sendAndReceive();
                    if (reply.isPresent()) {
                        System.out.println(reply.get());
                    } else {
                        log.error("Doesn't have reply");
                    }
                });

    }
    public void processRequest() {
        ShutDownThread.createAndAdd(serverChanel::close);
        try {
            authorize();
        } catch (AuthorizeException exception) {
            log.error(exception.getMessage());
            new Exit().prepare();
        }
        while (true) {

            Optional<Command> command = invoker.invoke(UserReader.read());
            serializeAndSend(command,
                    result -> {
                        if (result) {
                            Optional<String> reply = serverChanel.sendAndReceive();
                            if (reply.isPresent()) {
                                System.out.println(reply.get());
                            } else {
                                log.error("Doesn't have reply");
                            }
                        } else {
                            log.error("Can't send serialized message,it is empty");
                        }
                    }
            );
        }
    }

    private void authorize() {
        Optional<Command> command = Optional.empty();

        while (command.isEmpty()) {
            System.out.println("Choose one digit");
            System.out.println("Log in system-1");
            System.out.println("Sign up in system-2");
            System.out.println("Finish session-3");
            String answer = UserReader.read();
            answer = answer.trim();
            if (answer.equals("3")) {
                Exit exit = new Exit();
                exit.prepare();
            }

            if (answer.equals("2")) {
                SignIn signIn = new SignIn();
                command = signIn.prepare(null);
            }

            if (answer.equals("1")) {
                LogIn logIn = new LogIn();
                command = logIn.prepare(null);
            }
        }
        AtomicReference<Optional<String>> reply = new AtomicReference<>(Optional.empty());
        serializeAndSend(command, result -> {
            if (!result) {
                throw new AuthorizeException("Can't send Message to server");
            }
            reply.set(serverChanel.sendAndReceive());
            if (reply.get().isEmpty()) {
                throw new AuthorizeException("Connection with server lost,can't authorize now");
            }
            if (reply.get().get().contains("error")) {
                throw new AuthorizeException(reply.get().get());
            }
            log.info("Authorized successfully");
            Users.getUser().setToken(reply.get().get());
        });

    }

}
