package com.gson.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

public class Test {

    public static void main(String[] args) {
        test2();
//        test1();
    }

    private static void test1() {
        String json = "[{\"2011-04-30T00:00:00-0700\":100}, {\"2011-04-29T00:00:00-0700\":200}]";

        Gson gson =
                new GsonBuilder()
                        .registerTypeAdapter(MyCustomClass.class, new MyCustomDeserializer())
                        .create();
        Type collectionType = new TypeToken<Collection<MyCustomClass>>() {
        }.getType();
        Collection<MyCustomClass> myCustomClasses = gson.fromJson(json, collectionType);
        System.out.println(myCustomClasses);
    }

    private static void test2() {
        String json = "{\"code\":141,\"error\":{\"code\":111,\"message\":\"schema mismatch for ResourceFavorite.resource; expected Pointer<Resource> but got Pointer<ResourceFavorite>\"}}";

        Gson gson =
                new GsonBuilder()
                        .registerTypeAdapter(ParseErrorWrapper.class, new ParseErrorDeserilizer())
                        .create();
        Type collectionType = new TypeToken<ParseErrorWrapper>() {}.getType();
        ParseErrorWrapper myCustomClasses = gson.fromJson(json, collectionType);
        System.out.println(myCustomClasses);
        System.out.println(myCustomClasses.getErrorMessage());
    }
}
