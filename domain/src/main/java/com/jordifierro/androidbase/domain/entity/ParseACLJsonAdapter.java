package com.jordifierro.androidbase.domain.entity;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;

public class ParseACLJsonAdapter implements JsonDeserializer<ParsePermissionWrapper>, JsonSerializer<ParsePermissionWrapper> {

    @Override
    public ParsePermissionWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        final Set<Entry<String, JsonElement>> objectIdSet = obj.entrySet();
        if (objectIdSet == null || objectIdSet.size() == 0) {
            return null;
        }

        ArrayList<ParsePermission> permissionArrayList = new ArrayList<>(objectIdSet.size());


        for (Entry<String, JsonElement> objectIdJsonElementEntry : objectIdSet) {
            ParsePermission.Builder builder = ParsePermission.builder();
            builder.objectId(objectIdJsonElementEntry.getKey());
            final JsonElement value = objectIdJsonElementEntry.getValue();
            final JsonObject readWriteJsonObject = value.getAsJsonObject();
            boolean readable = readWriteJsonObject.get("read").getAsBoolean();
            boolean writable = readWriteJsonObject.get("write").getAsBoolean();
            builder.read(readable);
            builder.write(writable);
            permissionArrayList.add(builder.build());

        }

        return ParsePermissionWrapper.builder().permissionArrayList(permissionArrayList).build();
    }

    @Override
    public JsonElement serialize(ParsePermissionWrapper src, Type typeOfSrc, JsonSerializationContext context) {

        final JsonObject jsonObject = new JsonObject();


        for (ParsePermission permission : src.permissionArrayList()) {

            final JsonObject jsonObjectPermission = new JsonObject();
            jsonObjectPermission.addProperty("read", permission.read());
            jsonObjectPermission.addProperty("write", permission.read());

            jsonObject.add(permission.objectId(), jsonObjectPermission);
        }

        return jsonObject;

    }
}