package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping(value = "/")
    public String home() {
        return "chatroom";
    }

    @RequestMapping("/login")
    public String login() throws Exception {
        return "login";
    }
}
