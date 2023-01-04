package vip.corejava.app.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;
import java.util.Objects;

/**
 * 反序列号  json -> bean
 * @author xcl
 * @date 2021/11/17
 * @JsonDeserialize(using= AesJsonDeserializer.class)
 */
public class AesJsonDeserializer extends StringTrimDeserializer {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String deserialize = super.deserialize(p, ctxt);
        if (Objects.isNull(deserialize)) {
            return null;
        }
        return deserialize+":aes";
    }
}
