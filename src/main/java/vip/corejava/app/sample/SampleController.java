package vip.corejava.app.sample;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author xcl
 * @date 2022/11/28
 */
@RestController
@RequestMapping("/sample")
@Slf4j
@Validated
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
        resp.setIdCard("id," + req.idCard);
        resp.setPhone(req.phone);
        return resp;
    }


    @RequestMapping(path = "/part", consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public SampleDto.Resp index(@RequestParam(name = "name")  @NotBlank String name,
                                @RequestParam(name = "idCard") @NotBlank String idCard,
                                @RequestParam(name = "phone") @NotBlank String phone) {
        log.info("--------->{}", name);
        SampleDto.Resp resp = new SampleDto.Resp();
        resp.setName("hi," + name);
        resp.setIdCard("id," + idCard);
        resp.setPhone(phone);
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
