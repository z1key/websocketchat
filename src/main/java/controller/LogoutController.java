package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by UserMessage on 29.10.2017.
 */
@Controller
public class LogoutController {

    @RequestMapping("/logout")
    public String logout(HttpSession httpSession, WebSocketSession socketSession) throws IOException {
        httpSession.invalidate();
        socketSession.close(CloseStatus.NORMAL);
        return "index";
    }
}
