package itmo.p3108.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

@Slf4j
public class UDPSender {
    private DatagramSocket socket;
    private InetAddress address;
    private int serverPort;

    public UDPSender(int serverPort) {
        try {
            this.serverPort = serverPort;
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
        } catch (SocketException | UnknownHostException exception) {
            log.error(exception.toString());
            System.err.println("Error during creating UdpSender:can't create ");
            System.exit(1);
        }

    }

    public void send(String msg) {
        try {
            byte[] buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, serverPort);
            socket.send(packet);

        } catch (IOException exception) {
            System.err.println("Error during sending message:can't send message to server");
            log.error(exception.toString());

        }

    }

    public void send(byte[] msg) {
        try {
            ByteBuffer buffer = ByteBuffer.wrap(msg);
            DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.limit(), address, serverPort);
            socket.send(packet);
        } catch (IOException exception) {
            System.err.println("Error during sending message:can't send message to server");
            log.error(exception.toString());
        }

    }

    public void close() {
        socket.close();
    }
}
