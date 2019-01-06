package com.tony.tang.note.remote;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.tony.tang.note.domain.entity.PermissionItem;
import com.tony.tang.note.domain.entity.PermissionItemList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import dagger.Reusable;

@Reusable
public class ParseACLJsonAdapter implements JsonDeserializer<PermissionItemList>, JsonSerializer<PermissionItemList> {

    @Inject
    public ParseACLJsonAdapter() {
    }

    @Override
    public PermissionItemList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        final Set<Entry<String, JsonElement>> objectIdSet = obj.entrySet();
        if (objectIdSet == null || objectIdSet.size() == 0) {
            return null;
        }

        ArrayList<PermissionItem> permissionArrayList = new ArrayList<>(objectIdSet.size());


        for (Entry<String, JsonElement> objectIdJsonElementEntry : objectIdSet) {
            PermissionItem.Builder builder = PermissionItem.builder();
            builder.objectId(objectIdJsonElementEntry.getKey());
            final JsonElement value = objectIdJsonElementEntry.getValue();
            final JsonObject readWriteJsonObject = value.getAsJsonObject();
            boolean readable = readWriteJsonObject.get("read").getAsBoolean();
            boolean writable = readWriteJsonObject.get("write").getAsBoolean();
            builder.read(readable);
            builder.write(writable);
            permissionArrayList.add(builder.build());

        }

        return PermissionItemList.builder().permissionItemList(permissionArrayList).build();
    }

    @Override
    public JsonElement serialize(PermissionItemList src, Type typeOfSrc, JsonSerializationContext context) {

        final JsonObject jsonObject = new JsonObject();


        for (PermissionItem permission : src.permissionItemList()) {

            final JsonObject jsonObjectPermission = new JsonObject();
            jsonObjectPermission.addProperty("read", permission.read());
            jsonObjectPermission.addProperty("write", permission.read());

            jsonObject.add(permission.objectId(), jsonObjectPermission);
        }

        return jsonObject;

    }
}