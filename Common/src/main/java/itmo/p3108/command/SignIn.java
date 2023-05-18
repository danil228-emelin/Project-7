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
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class SignIn implements OneArgument<Users> {
    private Users users;

    public SignIn() {
        users = Users.getUser();
    }

    @Override
    public String description() {
        return "sign in user in system";
    }

    @Override
    public String name() {
        return "sign_in";
    }

    @Override
    public String execute(String token) {
        if (UsersStorage.isExist(token)) {
            log.info("error:authorization failed for " + users.getLogin());
            return "error:Login Already exist";
        }
        users.setToken(token);
        users.setPassword(Token.encrypt(users.getPassword()));
        UsersStorage.add(token, users);
        log.info("authorized in system " + users.getLogin());
        return token;
    }

    @Override
    public Users getParameter() {
        return users;
    }

    @Override
    public void setParameter(@NonNull Users parameter) {
        users = parameter;
    }

    @Override
    public Optional<Command> prepare(String argument) {
        System.out.println("Enter new login");
        String log = UserReader.read();
        Console console = System.console();
        String password;
        String passwordAgain;
        users.setLogin(log);
        do {
            System.out.println("Enter password");
            password = Arrays.toString(console.readPassword());
            System.out.println("Enter password again");
            passwordAgain = Arrays.toString(console.readPassword());
        } while (!password.equals(passwordAgain));
        users.setPassword(password);
        return Optional.of(this);
    }


}
