package me.bramhaag.guilds.database.databases.json;

import com.google.gson.*;
import me.bramhaag.guilds.guild.Guild;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GuildMapDeserializer implements JsonDeserializer<Map<String, Guild>> {
    @Override
    public Map<String, Guild> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        Map<String, Guild> guilds = new HashMap<>();
        object.entrySet().forEach(entry -> {
            JsonObject guild = entry.getValue().getAsJsonObject();
            guild.addProperty("name", entry.getKey());

            guilds.put(entry.getKey(), context.deserialize(guild, Guild.class));
        });

        for (Map.Entry entry : object.entrySet()) {

            System.out.println("key:" + entry.getKey());
            System.out.println("value:" + entry.getValue());
        }

        System.out.println(object);

        return guilds;
    }
}
