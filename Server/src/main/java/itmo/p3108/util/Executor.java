package itmo.p3108.util;

import itmo.p3108.chain.AuthorizeHandler;
import itmo.p3108.chain.Handler;
import itmo.p3108.exception.ValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Executor {
    private static final Handler HANDLER = new AuthorizeHandler();
    @Getter
    private static final ExecutorService POOL = Executors.newFixedThreadPool(10);

    public static void executeCommand(UDPSender udpSender, UDPReceiver receiver) {

        Optional<byte[]> serializedObject = receiver.receive();
        Runnable serverReply = () -> serializedObject.ifPresentOrElse(x -> {
            Optional<?> command = DeserializeObject.deserializeObject(x);
            command.ifPresentOrElse(co -> {
                if (co instanceof MessageServer messageServer) {
                    String result;
                    try {
                        result = HANDLER.processRequest(messageServer.getCommand(), messageServer.getToken());
                    } catch (ValidationException validationException) {
                        log.error(validationException.toString());
                        result = validationException.toString();
                    }
                    udpSender.send(result, messageServer.getPort());
                } else {
                    log.error("serializedObject isn't messageServer");
                }
            }, () -> log.error("message is incorrect,Can't deserialize"));
        }, () -> log.error("message is null"));

        CompletableFuture.runAsync(serverReply, POOL);

    }

}
