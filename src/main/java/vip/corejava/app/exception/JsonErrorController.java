package vip.corejava.app.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author xcl
 * @date 2022/12/1
 * ResponseEntityExceptionHandler
 */
@ControllerAdvice
@RestController
@Slf4j
public class JsonErrorController extends AbstractErrorController {

    public JsonErrorController() {
        super(new DefaultErrorAttributes());
    }

    @ResponseBody
    @RequestMapping("${server.error.path:${error.path:/error}}")
    public ResponseEntity error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE));
        return new ResponseEntity<>(body, status);
    }


    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    public ResponseEntity processException(NoHandlerFoundException e) {
        return ResponseEntity.notFound().build();
    }


    /**
     * 参数校验异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity processException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE));
        body.put("error", errors);
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * 参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({ServletRequestBindingException.class})
    @ResponseBody
    public ResponseEntity processException(ServletRequestBindingException e, HttpServletRequest request) {
        log.error("Exception", e);
        List<String> errors = new ArrayList<>();
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE));
        body.put("error", errors);
        return ResponseEntity.badRequest().body(body);
    }



    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity processException(Exception e, HttpServletRequest request) {
        log.error("Exception", e);
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE));
        return new ResponseEntity<>(body, status);
    }
}
