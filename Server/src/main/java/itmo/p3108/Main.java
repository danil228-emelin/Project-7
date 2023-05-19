package itmo.p3108;

import itmo.p3108.bd.PrepareServer;
import itmo.p3108.util.Executor;
import itmo.p3108.util.UDPReceiver;
import itmo.p3108.util.UDPSender;
import lombok.extern.slf4j.Slf4j;

/**
 * Entry point of program.
 * Before work with Clients preparation will happen
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        PrepareServer prepareServer = new PrepareServer();
        prepareServer.prepare();
        UDPSender udpSender = new UDPSender();
        UDPReceiver udpReceiver = new UDPReceiver(4445);
        while (true) {
            Executor.executeCommand(udpSender, udpReceiver);
        }

    }
}
