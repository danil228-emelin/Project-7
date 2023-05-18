package itmo.p3108.command;

import itmo.p3108.command.type.Command;
import itmo.p3108.command.type.OneArgument;
import itmo.p3108.exception.FileException;
import itmo.p3108.exception.ValidationException;
import itmo.p3108.util.FileWorker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serial;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Command Execute Script.
 * It checks all constraints before analyze fail.
 * After it pass file to AnalyzeExecuteScript
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ExecuteScript implements OneArgument<String> {
    @Serial
    private static final long serialVersionUID = 549988001L;
    private static final String ERROR_PERMISSION = "ExecuteScript error during execute script:file  doesn't exist or unreadable";
    private static int executingFails = 0;
    private final Set<Path> EXECUTED_FAILS = new HashSet<>();

    @Override
    public String description() {
        return null;
    }

    @Override
    public String name() {
        return "execute_script";
    }

    @Override
    public String execute(String t) {
       log.warn("execute script  doesn't execute ");
        return "";
    }

    @Override
    public String getParameter() {
        log.warn("execute String doesn't have parameter");

        return "";
    }

    @Override
    public void setParameter(@NonNull String parameter) {
    log.warn("execute script doesnt' have parameter");
    }

    @Override
    public Optional<Command> prepare(@NonNull String argument) {
        Path test;
        try {
            test = Path.of(argument);
        } catch (InvalidPathException exception) {;
            throw new FileException("Error during executing script:wrong file name");
        }
        if (!Files.exists(test) || !Files.isReadable(test)) {
            throw new FileException(ERROR_PERMISSION);
        }
        try {
            String[] commands = FileWorker.read(argument).split("\n");
            final int MAXIMUM_COMMANDS_IN_FILE = 15;
            if (commands.length > MAXIMUM_COMMANDS_IN_FILE) {
                throw new ValidationException(String.format("Error during executing script:%s has disallowed amount of commands ", argument));
            }
            final int MAXIMUM_FILES = 49;
            if (EXECUTED_FAILS.size() > MAXIMUM_FILES) {
                throw new ValidationException(String.format("Error during executing script:%s processed maximum filed already ", argument));
            }
            if (EXECUTED_FAILS.contains(test)) {
                throw new ValidationException(String.format("Error during executing script:Recursion is forbidden,file already executed %s", argument));
            }
            executingFails++;
            EXECUTED_FAILS.add(test);
            AnalyzeExecuteScript.analyze(commands);

        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
        executingFails--;
        if (executingFails == 0) {
            EXECUTED_FAILS.clear();
        }
        return Optional.empty();
    }
}

