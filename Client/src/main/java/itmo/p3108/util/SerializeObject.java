package itmo.p3108.util;

import itmo.p3108.command.type.Command;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * Serialize object before sending
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SerializeObject {

    @Getter
    private static final Queue<byte[]> messages = new LinkedList<>();
    @Getter
    @Setter
    private static int clientPort;

    private static boolean createMessage(MessageServer messageServer, ByteArrayOutputStream byteArrayOutputStream) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(messageServer);
            objectOutputStream.flush();
            messages.add(byteArrayOutputStream.toByteArray());
            return true;
        } catch (IOException exception) {
            log.error(exception.getMessage());
            return false;
        }
    }

    public static <T extends Command> boolean serialize(T command, int port) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            MessageServer messageServer = new MessageServer();
            messageServer.setCommand(command);
            if (port == 0) {
                log.info("port is 0");
                log.info("port is " + clientPort);
                messageServer.setPort(clientPort);
            } else {
                log.info("port is" + port);
                messageServer.setPort(port);
            }
            messageServer.setToken(Users.getUser().getToken());
            boolean result = createMessage(messageServer, byteArrayOutputStream);
            if (result) {
                log.info(String.format("serialize command %s", messageServer.getCommand().name()));
                return true;
            }
            return false;

        } catch (IOException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public static Optional<byte[]> poll() {
        return Optional.ofNullable(messages.poll());
    }

    public static void clear() {
        messages.clear();
    }
}
