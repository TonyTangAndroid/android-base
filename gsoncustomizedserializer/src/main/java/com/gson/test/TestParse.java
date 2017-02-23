package com.gson.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class TestParse {

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"name\": \"Team1\",\n" +
                "    \"createdAt\": \"2013-04-22T19:27:19.868Z\",\n" +
                "    \"updatedAt\": \"2013-04-22T19:49:39.252Z\",\n" +
                "    \"objectId\": \"HSO4N1JT3W\",\n" +
                "    \"ACL\": {\n" +
                "        \"3WQrZ0dyrt\": {\n" +
                "            \"read\": true,\n" +
                "            \"write\": true\n" +
                "        },\n" +
                "        \"mCLz2AdYts\": {\n" +
                "            \"read\": true,\n" +
                "            \"write\": true\n" +
                "        }\n" +
                "    }\n" +
                "}";

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ParsePermissionWrapper.class, new ParseACLJsonAdapter())
                .create();
        Type parseObjectType = new TypeToken<ParseNote>() {
        }.getType();
        ParseNote parseNote = gson.fromJson(json, parseObjectType);
        System.out.println(parseNote);
    }
}
