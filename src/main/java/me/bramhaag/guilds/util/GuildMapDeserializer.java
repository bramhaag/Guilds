package me.bramhaag.guilds.util;

import com.google.gson.*;
import me.bramhaag.guilds.guild.Guild;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bram on 24-12-2016.
 */
public class GuildMapDeserializer implements JsonDeserializer<Map<Integer, Guild>> {
    @Override
    public Map<Integer, Guild> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        Map<Integer, Guild> guilds = new HashMap<>();

        object.entrySet().forEach(entry -> guilds.put(Integer.valueOf(entry.getKey()), context.deserialize(entry.getValue(), Guild.class)));

        for (Map.Entry entry : object.entrySet()) {

            System.out.println("key:" + entry.getKey());
            System.out.println("value:" + entry.getValue());
        }

        System.out.println(object);

        return guilds;
    }
}
