package itmo.p3108.chain;

import java.util.Optional;

/**
 * Handler is used for processing request from Invoker.
 * Handler work with certain type of Command.
 * For instance OneArgumentHandler works with Command with 1 argument.
 * All handlers create patters chain or responsibility.
 * Aim of Handler-prepare command before sending to server
 * @param <T>
 */
public interface Handler<T> {
    Optional<T> processRequest(WrapperArgument wrapperArgument);


}
