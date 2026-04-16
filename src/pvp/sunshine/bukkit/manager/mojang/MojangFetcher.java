package pvp.sunshine.bukkit.manager.mojang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class MojangFetcher {

    public static synchronized boolean isPremium(String username) {
        try {
            HttpURLConnection connection = (HttpURLConnection)(new URL("https://api.mojang.com/users/profiles/minecraft/" + username)).openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("User-Agent", "Premium-Checker");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = bufferedReader.readLine();
            if (result != null && !result.equalsIgnoreCase("null"))
                return true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
