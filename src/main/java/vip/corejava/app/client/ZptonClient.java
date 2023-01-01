package vip.corejava.app.client;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

/**
 * @author xcl
 * @date 2022/12/12
 */
@FeignClient(name = "zptonClient", url = "https://mp2api.zpton.com", configuration = ZptonClient.Configuration.class)
public interface ZptonClient {




    @PostMapping(value = "/index/Login/passwordLogin",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String login(
            @RequestPart(value = "password") String password,
            @RequestPart(value = "username") String username
    );

    @PostMapping(value = "/index/Study/ChapterSectionList",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String ChapterSectionList(
            @RequestPart(value = "params[class_id]") String class_id,
            @RequestPart(value = "params[student_id]") String student_id,
            @RequestPart(value = "params[type]") String type,
            @RequestPart(value = "token") String token,
            @RequestPart(value = "username") String username
    );

    @PostMapping(value = "/index/Study/insStudyRecord",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String insStudyRecord(
            @RequestPart(value = "params[class_id]") String class_id,
            @RequestPart(value = "params[student_id]") String student_id,
            @RequestPart(value = "params[courseware_id]") int courseware_id,
            @RequestPart(value = "params[type]") String type,
            @RequestPart(value = "token") String token,
            @RequestPart(value = "username") String username
    );


    @PostMapping(value = "/index/Study/activityDetection",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String activityDetection(
            @RequestPart(value = "params[username]") String pusername,
            @RequestPart(value = "params[student_id]") String student_id,
            @RequestPart(value = "params[status]") int status,
            @RequestPart(value = "params[source]") int source,
            @RequestPart(value = "params[start_time]") String start_time,
            @RequestPart(value = "params[respond_time]") String respond_time,
            @RequestPart(value = "token") String token,
            @RequestPart(value = "username") String username
    );


    @PostMapping(value = "/index/Study/screenCuttingDuration",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String screenCuttingDuration(
            @RequestPart(value = "params[class_id]") String class_id,
            @RequestPart(value = "params[user_id]") String user_id,
            @RequestPart(value = "params[video_time]") int video_time,
            @RequestPart(value = "params[source]") int source,
            @RequestPart(value = "params[video_id]") int video_id,
            @RequestPart(value = "params[is_finished]") int is_finished,
            @RequestPart(value = "params[last_viewing_time]") int last_viewing_time,
            @RequestPart(value = "params[end_time]") String end_time,
            @RequestPart(value = "params[sub_video_time][video_time]") int sub_video_time,
            @RequestPart(value = "params[sub_video_time][video_close_time]") int sub_video_close_time,
            @RequestPart(value = "params[sub_video_time][video_start_time]") int sub_video_start_time,
            @RequestPart(value = "token") String token,
            @RequestPart(value = "username") String username
    );



    @Slf4j
    class Configuration {

        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }

        @Bean
        RequestInterceptor targetInterceptor() {
            return requestTemplate -> {
                requestTemplate.header("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
            };
        }


    }
}
