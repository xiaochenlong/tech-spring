package vip.corejava.app.json;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Objects;

/**
 * 序列号  bean -> json
 */
@JacksonStdImpl
public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    SensitiveStrategy sensitiveStrategy;

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(sensitiveStrategy.func.apply(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty property) throws JsonMappingException {
        //获取字段上的自定义脱敏注解
        Sensitive annotation = property.getAnnotation(Sensitive.class);
        //注解需要不为null而且需要是String类型
        if (Objects.nonNull(annotation) && Objects.equals(String.class,property.getType().getRawClass())){
            this.sensitiveStrategy=annotation.strategy();
            return this;
        }
        return serializerProvider.findValueSerializer(property.getType(), property);
    }
}
