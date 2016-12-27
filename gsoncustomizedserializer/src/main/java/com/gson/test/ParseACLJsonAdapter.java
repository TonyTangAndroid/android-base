package com.gson.test;

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

        ParsePermissionWrapper permissionWrapper = new ParsePermissionWrapper();

        ArrayList<ParsePermission> permissionArrayList = new ArrayList<>(objectIdSet.size());


        for (Entry<String, JsonElement> objectIdJsonElementEntry : objectIdSet) {
            ParsePermission permission = new ParsePermission();
            permission.setObjectId(objectIdJsonElementEntry.getKey());
            final JsonElement value = objectIdJsonElementEntry.getValue();
            final JsonObject readWriteJsonObject = value.getAsJsonObject();
            boolean readable = readWriteJsonObject.get("read").getAsBoolean();
            boolean writable = readWriteJsonObject.get("write").getAsBoolean();
            permission.setRead(readable);
            permission.setWrite(writable);
            permissionArrayList.add(permission);

        }

        permissionWrapper.setPermissionArrayList(permissionArrayList);
        return permissionWrapper;
    }


    //	{
//		" +
//		"        \"3WQrZ0dyrt\": {\n" +
//				"            \"read\": true,\n" +
//				"            \"write\": true\n" +
//				"        },\n" +
//				"        \"mCLz2AdYts\": {\n" +
//				"            \"read\": true,\n" +
//				"            \"write\": true\n" +
//				"        }\n" +
//				"    }
//
//
    @Override
    public JsonElement serialize(ParsePermissionWrapper src, Type typeOfSrc, JsonSerializationContext context) {

        final JsonObject jsonObject = new JsonObject();


        for (ParsePermission permission : src.getPermissionArrayList()) {

            final JsonObject jsonObjectPermission = new JsonObject();
            jsonObjectPermission.addProperty("read", permission.isRead());
            jsonObjectPermission.addProperty("write", permission.isRead());

            jsonObject.add(permission.getObjectId(), jsonObjectPermission);
        }

        return jsonObject;

    }
}