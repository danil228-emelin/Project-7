package itmo.p3108.command;

import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.util.Token;
import itmo.p3108.util.UserReader;
import itmo.p3108.util.Users;
import itmo.p3108.util.UsersStorage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.Console;
import java.io.Serial;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class LogIn implements OneArgument<Users> {
    @Serial
    private static final long serialVersionUID = 465237001L;
    private Users users;

    public LogIn() {
        users = Users.getUser();
    }

    @Override
    public String description() {
        return "log in user in system";
    }

    @Override
    public String name() {
        return "log_in";
    }


    @Override
    public String execute(String token) {

        Optional<Users> logUser = UsersStorage
                .get(token);
        if (logUser.isEmpty()) {
            log.info(String.format("User with login %s doesn't exist", users.getLogin()));
            return String.format("error:User with login %s doesn't exist", users.getLogin());
        }
        if (logUser
                .get()
                .getPassword()
                .equals(Token.encrypt(users.getPassword()))) {
            log.info("authorized in system " + logUser.get().getLogin());
            return token;
        }
        log.info(String.format("error:wrong password for user %s", users.getLogin()));
        return String.format("wrong password for user %s", users.getLogin());
    }

    @Override
    public Users getParameter() {
        return users;
    }

    @Override
    public void setParameter(@NonNull Users parameter) {
        users = parameter;
    }

    //todo вернуть
    @Override
    public Optional<Command> prepare(String argument) {
        System.out.println("Enter login");
//        String log = UserReader.read();
        String log = "test1";
        Console console = System.console();
        users.setLogin(log);
        System.out.println("Enter password");
        String password = "1";
//        String password = Arrays.toString(console.readPassword());
        users.setPassword(password);
        return Optional.of(this);
    }
}
