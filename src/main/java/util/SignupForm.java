package util;

import domain.User;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Pattern;

public class SignupForm {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String PASSWORD_MESSAGE = "Wrong password";

    @NotBlank
    @Length(min = 6, max = 40)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$", message = PASSWORD_MESSAGE)
    private String password;

    private String verifyPassword;

    @NotBlank
    @Length(min = 5, max = 25)
    @Pattern(regexp = "^[A-Za-z\\d]+$", message = "Bad login.")
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User createAccount() {
        return new User(getLogin(), passwordEncoder.encode(getPassword()));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}
