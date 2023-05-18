package itmo.p3108.util;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
public class Users implements Serializable {

    @Serial
    private static final long serialVersionUID = 493737051L;

    private static final Users USER = new Users();
    private String login;
    private String password;
    private String token;
    private Boolean isSaved = false;
    private Integer UserId;
    public Users() {
    }

    public static Users getUser() {
        return USER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(login, users.login) && Objects.equals(password, users.password) && Objects.equals(token, users.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, token);
    }
}
