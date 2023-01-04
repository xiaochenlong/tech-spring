package vip.corejava.app.sample;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import vip.corejava.app.json.AesJsonDeserializer;

/**
 * @author xcl
 * @date 2022/12/13
 */
@Data
public class SampleDto {

    @Data
    public static class Req {

        @NotBlank
        public String name;

        @JsonDeserialize(using= AesJsonDeserializer.class)
        public String idCard;

    }

    @Data
    public static class Resp {
        public String name;

        public String idCard;
    }

}
