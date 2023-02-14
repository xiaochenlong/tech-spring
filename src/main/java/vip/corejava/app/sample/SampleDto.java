package vip.corejava.app.sample;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import vip.corejava.app.json.AesJsonDeserializer;
import vip.corejava.app.json.AesJsonSerializer;
import vip.corejava.app.json.Sensitive;
import vip.corejava.app.json.SensitiveStrategy;

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

        /**
         * 解密
         */
        @JsonDeserialize(using = AesJsonDeserializer.class)
        public String idCard;

        public String phone;

    }

    @Data
    public static class Resp {
        public String name;

        /**
         * 加密
         */
        @JsonSerialize(using = AesJsonSerializer.class)
        public String idCard;

        /**
         * 脱敏处理
         */
        @Sensitive(strategy = SensitiveStrategy.PHONE)
        public String phone;

    }

}
