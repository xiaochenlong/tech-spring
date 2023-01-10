package vip.corejava.app.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vip.corejava.app.client.OpenClient;
import vip.corejava.app.client.ZptonClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xcl
 * @date 2022/11/28
 */
@SpringBootTest
@Slf4j
public class ZptTest {

    @Resource
    ZptonClient zptonClient;

    String token = "WkhBTkdTaXIyNDgyNzFmNzVmY2FjYTQ4MzgzYTRjMzgyNjAwZDNhMg==";
    String username = "18516690799";
    String classId = "3744";
    String type = "1";
    String studentId = "65992";


    @Resource
    ObjectMapper objectMapper;

    @Test
    void login() throws JsonProcessingException {
        String resp = zptonClient.login("3354333256267a24d858b1177205e691", "18516690799");
        JsonNode jsonNode = objectMapper.readTree(resp);
        JsonNode responseBody = jsonNode.get("responseBody");
        String id = responseBody.get("id").asText();
        String username = responseBody.get("username").asText();
        String token = responseBody.get("token").asText();
        log.info("---->{},{},{}", id, username, token);
    }

    @Test
    void t0() throws IOException, InterruptedException {
        String resp = zptonClient.ChapterSectionList(classId, studentId, type, token, username);
        JsonNode jsonNode = objectMapper.readTree(resp);
        JsonNode responseBody = jsonNode.get("responseBody");
        JsonNode knowledge = responseBody.get("knowledge");
        int size = knowledge.size();
        System.out.println(size);
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        int buf = 0;
        for (int i = 0; i < size; i++) {
            JsonNode node = knowledge.get(i);
            JsonNode section = node.get("section");
            int ssize = section.size();
            for (int l = 0; l < ssize; l++) {
                JsonNode s = section.get(l);
                JsonNode video = s.get("video");
                int vsize = video.size();
                for (int vi = 0; vi < vsize; vi++) {
                    JsonNode v = video.get(vi);
                    int id = v.get("id").asInt();
                    String video_time = v.get("video_time").asText();
                    int videoTime = LocalTime.parse(video_time).toSecondOfDay();
                    int is_finished = v.get("is_finished").asInt();
                    int viewed_time = v.get("viewed_time").asInt();
                    log.info("---------{}:{}:{}:{}", id, videoTime, is_finished, viewed_time);
                    if (viewed_time < videoTime) {
                        buf += (videoTime - viewed_time);
                    }
                    if (is_finished == 20 && viewed_time == 0) {
                        threadPool.submit(() -> {
                            try {
                                Thread.sleep(3 * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //String record = zptonClient.insStudyRecord(classId, studentId, id, type, token, username);
                            //log.info("---------{}", record);
                            LocalDateTime dateTime = LocalDateTime.now();
                            String format = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
                            try {
                                Thread.sleep(videoTime * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            String cuttingDuration = zptonClient.screenCuttingDuration(classId, studentId, videoTime + 2,
                                    1, id, 1,
                                    videoTime,
                                    format, videoTime, videoTime,
                                    viewed_time, token, username);
                            log.info("---------{}", cuttingDuration);
                        });
                    }
                }

            }
        }
        log.info("qqq---------{}", buf);
        log.info("xxx---------{}", buf / 60);
        //System.in.read();
    }

    @Test
    void t1() {
        log.info("---------{}", LocalDateTime.now());
        int coursewareId = 174441;

        String resp = zptonClient.insStudyRecord(classId, studentId, coursewareId, type, token, username);
        log.info("---------{}", resp);
    }

    @Test
    void name() {
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        String detection = zptonClient.activityDetection(username, studentId, 1, 2, format, format, token, username);
        log.info("detection---------{}", detection);
    }

    @Test
    void t1_cuttingDuration() throws InterruptedException {


        int id = 174475;
        int videoTime = 1518;
        String record = zptonClient.insStudyRecord(classId, studentId, id, type, token, username);
        log.info("insStudyRecord---------{}", record);
        Thread.sleep(videoTime * 1000);
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        String cuttingDuration = zptonClient.screenCuttingDuration(classId, studentId, videoTime + 2,
                1, id, 1,
                videoTime,
                format, videoTime, videoTime,
                0, token, username);
        log.info("screenCuttingDuration---------{}", cuttingDuration);
    }
}
