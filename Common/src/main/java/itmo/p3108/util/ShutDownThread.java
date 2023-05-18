package itmo.p3108.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShutDownThread {


    public static void add(Thread thread) {
        Runtime.getRuntime().addShutdownHook(thread);
    }

    public static void createAndAdd(Runnable runnable) {
        Thread thread = new Thread(runnable);
        add(thread);
        log.info("+1 thread executed after main");
    }

}
