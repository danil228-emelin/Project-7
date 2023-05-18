package itmo.p3108.chain;

import itmo.p3108.command.type.Command;

public interface Handler {
    /**
     * Handler for different command type
     */
    String processRequest(Command command,String token);
}
