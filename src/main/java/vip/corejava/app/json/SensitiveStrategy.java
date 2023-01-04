package vip.corejava.app.json;


import java.util.function.Function;

/**
 * 自定义脱敏策略
 */
public enum SensitiveStrategy {

    /**
     * 用户名脱敏
     */
    USERNAME(s -> s.replaceAll("(.).*", "$1***")),

    /**
     * 手机号脱敏
     */
    PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),

    /**
     * 身份证脱敏
     */
    ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2")),

    ;

    /**
     * 一个function函数
     */
    public final Function<String, String> func;

    SensitiveStrategy(Function<String, String> serializer) {
        this.func = serializer;
    }


}
