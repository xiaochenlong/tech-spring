package vip.corejava.app.sample;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.Future;

/**
 * @author xcl
 * @date 2022/11/28
 */
@RestController
@RequestMapping("/sample")
@Slf4j
public class SampleController {

    @RequestMapping("/")
    public String home() {
        SecurityContext context = SecurityContextHolder.getContext();
        return "Hello World!" + context.getAuthentication();
    }

    @RequestMapping("/index")
    public SampleDto.Resp index(@RequestBody @Valid SampleDto.Req req) {
        log.info("--------->{}", req);
        SampleDto.Resp resp = new SampleDto.Resp();
        resp.setName("hi," + req.name);
        return resp;
    }


    @RequestMapping("/async")
    @Async
    public void async() {
        log.info("--------->async");
        SampleDto.Resp resp = new SampleDto.Resp();
        resp.setName("helo," + LocalDateTime.now());
    }

}
