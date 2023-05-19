package itmo.p3108.util;

import itmo.p3108.exception.FileException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * ServerChanel keep classes for sending and receive data
 */
@Slf4j
@Data
public class ServerChanel {
    private UDPSender udpSender;
    private UDPReceiver udpReceiver;
    private int serverPort;
    private ConnectionServerState state;
    private int clientPort;

    public ServerChanel(int serverPort) {
        this.state = new ConnectionServerState();
        this.serverPort = serverPort;
        while (true) {
            try {
                clientPort = (int) (8000 + Math.random() * 2000);
                this.udpReceiver = new UDPReceiver(clientPort);
                break;
            } catch (IllegalArgumentException ignored) {
            }
        }
        SerializeObject.setClientPort(clientPort);
        this.udpSender = new UDPSender(serverPort);
    }


    public Optional<String> sendAndReceive() {

        try {
            return Optional.ofNullable(state.processNormalConnection(this));
        } catch (FileException exception) {
            log.error(exception.getMessage());
            return Optional.empty();
        }
    }

    public void close() {
        udpSender.close();
        udpReceiver.close();
    }
}
