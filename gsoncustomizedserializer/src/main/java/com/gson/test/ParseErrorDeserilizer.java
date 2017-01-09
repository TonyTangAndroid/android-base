package com.gson.test;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class ParseErrorDeserilizer implements JsonDeserializer<ParseErrorWrapper> {


    @Override
    public ParseErrorWrapper deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return doDeserilizer(json);
    }

    private ParseErrorWrapper doDeserilizer(JsonElement json) {
        ParseErrorWrapper options = new Gson().fromJson(json, ParseErrorWrapper.class);
        JsonObject jsonObject = json.getAsJsonObject();

        if (jsonObject.has("error")) {
            JsonElement elem = jsonObject.get("error");
            if (elem != null) {
                if (elem.isJsonObject()) {
                    ErrorBean errorBean = new Gson().fromJson(elem, ErrorBean.class);
                    options.setErrorBean(errorBean);

                } else {
                    String error = elem.getAsString();
                    ErrorBean errorBean = new ErrorBean(error, jsonObject.get("code").getAsInt());
                    options.setErrorBean(errorBean);

                }
            }

        }
        return options;
    }

}