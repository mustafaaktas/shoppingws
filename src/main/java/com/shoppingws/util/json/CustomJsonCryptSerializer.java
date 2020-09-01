package com.shoppingws.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.vor.onlinecheckin.util.Helper;

import java.io.IOException;

public class CustomJsonCryptSerializer extends JsonSerializer<String> implements ContextualSerializer {
    public CustomJsonCryptSerializer() {
        super();
    }

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(Helper.encrypt(s));
    }


    public CustomJsonCryptSerializer(String dateFormat) {
        super();

    }
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        return null;
    }
}
