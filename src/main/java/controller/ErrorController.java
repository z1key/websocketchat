package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

/**
 * Created by UserMessage on 30.10.2017.
 */
//@ControllerAdvice
public class ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception error, Model model) {
        logger.error("Error was: " + error.getMessage(), error);
        model.addAttribute("message", error.getMessage());
        model.addAttribute("stackTrace", error.getStackTrace());
        model.addAttribute("exception", error);
        return "error";
    }
}
