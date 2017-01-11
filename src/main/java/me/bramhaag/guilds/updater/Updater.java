package me.bramhaag.guilds.updater;

import com.google.gson.Gson;
import me.bramhaag.guilds.Main;
import me.bramhaag.guilds.database.Callback;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class Updater {

    private static int RESOURCE_ID = 13388;
    private static final String USER_AGENT = "Guilds_Update_Checker";

    private static final String API_URL = "https://api.spiget.org/v2/resources";
    private static final String SPIGOT_URL = "https://www.spigotmc.org/resources";

    private static UpdateResponse response = null;

    private static Gson gson = new Gson();

    public static void checkForUpdates(Callback<Boolean, Exception> callback) {
        Main.newChain()
            .asyncFirst(Updater::getLatestVersion)
            .syncLast((latestVersion) -> {
                response = latestVersion;
                long creationTime = Main.getCreationTime();
                System.out.println("CREATED: " + creationTime);
                System.out.println("Last version: " + latestVersion.getReleaseDate());
                System.out.println("Last version > created: " + (latestVersion.getReleaseDate() > creationTime));
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
                        //FileUtils.copyURLToFile(new URL();

                        URL url = new URL(String.format("%s/%s/download?version=%s", SPIGOT_URL, RESOURCE_ID, response.getId()));

                        URLConnection connection = url.openConnection();
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
                        connection.connect();
                        FileUtils.copyInputStreamToFile(connection.getInputStream(), Main.getInstance().getDataFolder().getParentFile());

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
            //System.out.println(IOUtils.toString(stream, Charset.defaultCharset()));

            UpdateResponse[] result = gson.fromJson(IOUtils.toString(stream, Charset.defaultCharset()), UpdateResponse[].class);
            return result[0];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
