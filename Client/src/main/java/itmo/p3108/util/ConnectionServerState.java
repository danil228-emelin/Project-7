package itmo.p3108.util;

import itmo.p3108.exception.FileException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Optional;

/**
 * ConnectionServerState IS responsible for process different connection situations with Server.
 */
@Data
@Slf4j
public class ConnectionServerState {

    /**
     * Use when connection is stable and we can send commands
     */
    public String processNormalConnection(ServerChanel serverChanel) {
        StringBuilder builder = new StringBuilder();
        while (true) {
            Optional<byte[]> message = SerializeObject.poll();
            if (message.isEmpty()) {
                if (builder.toString().length() == 0) {
                    throw new FileException("Message is null.");
                }
                return builder.toString();
            }
            serverChanel.getUdpSender().send(message.get());
            log.info("Message sent to server,waiting for reply");
            Optional<InetSocketAddress> inetSocketAddress = serverChanel.getUdpReceiver().receive();
            if (inetSocketAddress.isEmpty()) {
                throw new FileException("connection with server lost");
            }
            builder.append(createMessage(serverChanel.getUdpReceiver().getBuffer()));
        }
    }


    private String createMessage(ByteBuffer buffer) {
        buffer.flip();
        int limit = buffer.limit();
        byte[] bytes = new byte[limit];
        buffer.get(bytes, 0, limit);
        String message = new String(bytes);
        buffer.clear();
        return message;
    }

}
