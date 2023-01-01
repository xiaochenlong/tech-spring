package vip.corejava.app.sample;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
    }

    @Data
    public static class Resp {
        public String name;
    }

}
