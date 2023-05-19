package itmo.p3108.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Optional;

@Slf4j
/**
 * receive from client.
 */
public class UDPReceiver {
    private DatagramSocket socket;

    private ByteBuffer buf = ByteBuffer.allocate(65508);

    public UDPReceiver(int ServerPort) {
        try {
            log.info("Create  DatagramSocket to receive messages");
            socket = new DatagramSocket(ServerPort);
        } catch (SocketException e) {
            log.error(e.toString());
            System.exit(1);
        }
    }

    public Optional<byte[]> receive() {
        try {
            buf.clear();
            DatagramPacket packet;
            packet = new DatagramPacket(buf.array(), buf.limit());
            socket.setSoTimeout(0);
            socket.receive(packet);
            log.info("receive message");

            return Optional.of(packet.getData());
        } catch (IOException exception) {
            log.error(exception.toString());
            return Optional.empty();
        }

    }

    public void close() {
        socket.close();
    }
}
