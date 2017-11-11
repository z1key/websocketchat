package controller;

import domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private static final String CHANNEL = "/system/joined";

    private SimpMessagingTemplate template;

    @Autowired
    public HomeController(SimpMessagingTemplate messagingTemplate) {
        this.template = messagingTemplate;
    }

    @RequestMapping(value = "/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("chatroom");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            template.convertAndSend(CHANNEL, new Message("System", "User " + user.getUsername() + " has joined chat!"));
            modelAndView.addObject("user", user);
        }
        return modelAndView;
    }
}
