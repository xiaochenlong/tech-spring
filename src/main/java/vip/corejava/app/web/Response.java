package vip.corejava.app.web;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xcl
 * @date 2021/8/29
 */
@Data
@Accessors(chain = true)
public class Response<T> {
    private int code;

    private String msg;

    private T data;

    public static Response f() {
        return new Response();
    }

    public static Response ok() {
        return f().setCode(200).setMsg("success");
    }

    public static <Body> Response ok(Body body) {
        return ok().setData(body);
    }

    public static Response serverError() {
        return f().setCode(500).setMsg("server error");
    }

    public static Response badRequest() {
        return f().setCode(400).setMsg("参数错误");
    }
}
