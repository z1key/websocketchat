package controller;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.IUserService;
import util.AjaxUtils;
import util.SignupForm;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

/**
 * Created by User on 21.11.2017.
 */
@Controller
public class RegistrationController {

    private static final String SIGNUP_VIEW_NAME = "signup";

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/register")
    public String signup(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
        model.addAttribute(new SignupForm());
        if (AjaxUtils.isAjaxRequest(requestedWith)) {
            return SIGNUP_VIEW_NAME.concat(" :: signupForm");
        }
        return SIGNUP_VIEW_NAME;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors) {
        if (errors.hasErrors()) {
            return SIGNUP_VIEW_NAME;
        }
        User user = signupForm.createAccount();
        try {
            userService.persist(user);
        } catch (EntityExistsException ex) {
            errors.rejectValue("login", "user.exists");
            return SIGNUP_VIEW_NAME;
        }
        userService.signin(user);

        return "/";
    }
}
