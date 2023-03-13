package vip.corejava.app.sample;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

public class AudioTest {
    public static final String APP_ID = "31s1s382e19";
    public static final String API_KEY = "o1AGd2eiPkGsBXDK5sFYuoYxST";
    public static final String SECRET_KEY = "Z1tNs0U53HeE8dCseslwmIsb1pGTkjsrt16scj";

    @Test
    void name() throws JSONException {

        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "5");
        options.put("pit", "5");
        options.put("per", "5003");

        // 调用接口
        TtsResponse res = client.synthesis("数字信封是一种用于将数字证书和其它加密数据发送给接收方的方法。数字信封格式通常包括以下组成部分", "zh", 1, options);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, "target/output.mp3");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }

    }
}
