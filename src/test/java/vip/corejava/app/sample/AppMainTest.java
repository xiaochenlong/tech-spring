package vip.corejava.app.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vip.corejava.app.client.OpenClient;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xcl
 * @date 2022/11/28
 */
@SpringBootTest
@Slf4j
public class AppMainTest {

    @Resource
    OpenClient openClient;


    @Resource
    private ObjectMapper objectMapper;

    @Test
    void json() throws JsonProcessingException {
        Map<String,Object> map = new HashMap<>();
        map.put("a","v");
        String s = objectMapper.writeValueAsString(map);
        System.out.println(s);

        Map map1 = objectMapper.readValue(s, Map.class);
        System.out.println(map1);
        String s1="111";
        System.out.println(objectMapper.readValue(s1, Map.class));

    }

    @Test
    void t1() {

        log.info("---------{}", LocalDateTime.now());
        String token = openClient.token("35feec17-cd8d-48c0-bce2-4105fb74ca92");
        log.info("---------{}", token);
    }
}
