package service;

import domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by User on 21.11.2017.
 */
public interface IUserService extends UserDetailsService {
    void persist(User user);
    void signin(User user);
}
