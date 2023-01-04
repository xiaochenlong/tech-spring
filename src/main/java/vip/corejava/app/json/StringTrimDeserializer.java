package vip.corejava.app.json;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;


/**
 * 反序列号  json -> bean
 * 去除空格
 */
@JacksonStdImpl
public class StringTrimDeserializer extends StringDeserializer {

    public final static StringTrimDeserializer instance = new StringTrimDeserializer();

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String deserialize = super.deserialize(p, ctxt);
        return deserialize == null ? null : deserialize.trim();
    }
}
