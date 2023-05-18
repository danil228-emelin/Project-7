package itmo.p3108.util;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Optional;

@Slf4j
public class UDPReceiver {
    private static final int CAPACITY = 100_000;
    private static final int AMOUNT_OF_TRY = 100;
    private static final int WAIT_REPLY_MILLISECONDS = 100;
    @Getter
    private final ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
    private DatagramChannel channel;
    private InetSocketAddress address;

    public UDPReceiver(int clientPort) {
        try {
            address = new InetSocketAddress("localhost", clientPort);
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.bind(address);
        } catch (IOException exception) {
            System.err.println("Error during creating UDPReceiver:Can't create datagramChannel");
            log.error(exception.toString());
        }

    }


    public Optional<InetSocketAddress> receive() {
        int try_counter = 0;
        InetSocketAddress receiver = null;
        while (try_counter < AMOUNT_OF_TRY) {
            try {
                receiver = (InetSocketAddress) channel.receive(buffer);
                if (receiver == null) {
                    try_counter = try_counter + 1;
                    Thread.sleep(WAIT_REPLY_MILLISECONDS);
                } else {
                    break;
                }
            } catch (InterruptedException exception) {
                log.error(exception.toString());
                System.err.println("Error during receiving answer from server:Somebody tried to interrupted thread");
            } catch (IOException e) {
                log.error(e.toString());
                System.err.println("Error during receiving answer from server.");
                return Optional.empty();
            }
        }
        return Optional.ofNullable(receiver);
    }


    public void close() {
        try {
            channel.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}