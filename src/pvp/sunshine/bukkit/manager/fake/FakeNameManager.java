package pvp.sunshine.bukkit.manager.fake;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FakeNameManager {
    private static final Map<UUID, String> fakeNames = new HashMap<>();
    private static final Map<UUID, String> realNames = new HashMap<>();

    public static void setFakeName(UUID playerUUID, String fakeName) {
        fakeNames.put(playerUUID, fakeName);
    }
    public static void setRealName(UUID playerUUID, String RealName) {
       realNames.put(playerUUID, RealName);
    }

    public static String getFakeName(UUID playerUUID) {
        return fakeNames.get(playerUUID);
    }
    


    public static String getRealName(UUID playerUUID) {
        return realNames.get(playerUUID);
    }

    public static void removeFakeName(UUID playerUUID) {
        fakeNames.remove(playerUUID);
    }
    
    public static void removeRealNameCache(UUID playerUUID) {
        realNames.remove(playerUUID);
    }

    public static Map<UUID, String> getFakeNames() {
        return new HashMap<>(fakeNames);
    }
    public static Map<UUID, String> getRealNames() {
        return new HashMap<>(realNames);
    }
}
