package wanna_shop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception ex) {
        log.error("Exception occurred: " + ex.getClass(), ex);
        model.addAttribute("errType", ex.getClass().getSimpleName());
        model.addAttribute("errMsg", ex.getMessage());
        return "error";
    }
}

