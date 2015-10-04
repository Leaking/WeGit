package com.quinn.httpknife.payload;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.quinn.httpknife.github.Event;

import java.lang.reflect.Type;

/**
 * Created by Quinn on 15/10/2.
 */
public class EventFormatter implements JsonDeserializer<Event> {
    private final Gson gson;

    public EventFormatter() {
        this.gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Event event = (Event)this.gson.fromJson(json, Event.class);
        if(event != null && json.isJsonObject()) {
            JsonElement rawPayload = json.getAsJsonObject().get("payload");
            if(rawPayload != null && rawPayload.isJsonObject()) {
                String type = event.getType();
                if(type != null && type.length() != 0) {
                    Class payloadClass;
                    if("MemberEvent".equals(type)) {
                        payloadClass = MenberPayload.class;
                    } else if("IssuesEvent".equals(type)){
                        payloadClass = IssuePayload.class;
                    }else {
                        payloadClass = Payload.class;
                    }
                    try {
                        Payload payload = (Payload)context.deserialize(rawPayload, payloadClass);
                        event.setPayload(payload);
                        return event;
                    } catch (JsonParseException var9) {
                        return event;
                    }
                } else {
                    return event;
                }
            } else {
                return event;
            }
        } else {
            return event;
        }
    }
}
