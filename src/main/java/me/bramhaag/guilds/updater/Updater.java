package me.bramhaag.guilds.updater;

import com.google.gson.Gson;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.Callback;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Updater {

    private static int RESOURCE_ID = 13388;
    private static final String USER_AGENT = "Guilds_Update_Checker";

    private static final String API_URL = "https://api.spiget.org/v2/resources/";
    private static final String SPIGOT_URL = "https://www.spigotmc.org/resources/";


    private static UpdateResponse response = null;

    private static Gson gson = new Gson();

    public static void checkForUpdates(Callback<Boolean, Exception> callback) {
        Main.newChain()
            .asyncFirst(Updater::getLatestVersion)
            .syncLast((latestVersion) -> {
                response = latestVersion;
                long creationTime = Main.getCreationTime();
                callback.call(creationTime != 0 && latestVersion.getReleaseDate() > creationTime, null);
            })
            .execute();
    }

    public static void downloadUpdate(Callback<Boolean, Exception> callback) {
        if(response == null) {
            return;
        }

        Main.newChain()
                .asyncFirst(() -> {
                    try {
                        FileUtils.copyURLToFile(new URL(String.format("%s/download?version=%s", RESOURCE_ID, response.getId())), Main.getInstance().getDataFolder().getParentFile());
                        return true;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .syncLast((result) -> callback.call(result, null))
                .execute();
    }

    private static UpdateResponse getLatestVersion() {
        try {
            URL url = new URL(String.format("%s/%s/versions?size=1&sort=-releaseDate", API_URL, RESOURCE_ID));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", USER_AGENT);

            InputStream stream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);

            return gson.fromJson(reader, UpdateResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
