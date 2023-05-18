package itmo.p3108.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

@Slf4j
/**
 * Send to client.
 */
public class UDPSender {
    private final ByteBuffer buffer = ByteBuffer.allocate(100_000);
    private DatagramChannel channel;

    public UDPSender() {
        try {
            channel = DatagramChannel.open();
            channel.bind(null);
            channel.configureBlocking(false);
            log.info("Create DataGramChannel to send messages");
        } catch (IOException exception) {
            log.error(exception.toString());
        }

    }

    public void send(String message, int clientPort) {
        try {
            buffer.clear();
            buffer.put(message.getBytes());
            buffer.flip();
            InetSocketAddress serverAddress = FlyweightClientSocket.getClientSocket(clientPort);
            channel.send(buffer, serverAddress);
            log.info("send message back to client " + clientPort);
        } catch (IOException exception) {
            log.error(exception.toString());
        }
    }

    public void close() {
        try {
            channel.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
