package itmo.p3108.util;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UsersStorage {
    private static final ConcurrentHashMap<String, Users> map = new ConcurrentHashMap<>();

    public static void add(String token, Users users) {
        map.put(token, users);
    }
    public static boolean isExist(String token) {
        return map.containsKey(token);
    }
    public static Optional<Users> get(String key) {
        return Optional.ofNullable(map.get(key));
    }
    public static int length() {
        return map.size();
    }
    public static Set<Map.Entry<String, Users>> elements() {
        return map.entrySet();
    }
}
