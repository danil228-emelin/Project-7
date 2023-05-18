package itmo.p3108.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlyweightClientSocket {
    private static final Map<String, InetSocketAddress> CLIENT_SOCKET = new HashMap<>();

    public static InetSocketAddress getClientSocket(Integer clientPort) {
        if (CLIENT_SOCKET.containsKey(Integer.toString(clientPort))) {
            return CLIENT_SOCKET.get(Integer.toString(clientPort));
        }
        InetSocketAddress clientAddress = new InetSocketAddress("localhost", clientPort);
        CLIENT_SOCKET.put(Integer.toString(clientPort), clientAddress);
        return clientAddress;
    }

}
