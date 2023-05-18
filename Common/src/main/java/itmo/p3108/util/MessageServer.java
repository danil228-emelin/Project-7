package itmo.p3108.util;

import itmo.p3108.command.type.Command;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MessageServer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1234567L;
    private Command command;
    private int port;
    private String token;

}
