package wanna_shop.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wanna_shop.model.User;
import wanna_shop.persistence.UserDao;
import wanna_shop.persistence.UserDaoImpl;

@Slf4j
@Controller
public class UserController {
    @PostMapping("register")
    public String register(@RequestParam(name="username") String username,
                           @RequestParam(name="password") String password,
                           @RequestParam(name="confirmPass") String confirmPass,
                           @RequestParam(name="first", required = false) String first,
                           @RequestParam(name="last", required = false) String last,
                           @RequestParam(name="email") String email,
                           Model model){
        String errorMsg = null;
        if(username == null || username.isBlank()){
            errorMsg = "Cannot register without a username";
        }
        else if(password == null || password.isBlank()){
            errorMsg = "Cannot register without a password";
        }
        else if(confirmPass == null || confirmPass.isBlank() || !confirmPass.equals(password)){
            errorMsg = "Passwords must match!";
        }
        else if(email == null || email.isBlank()){
            errorMsg = "Cannot register without a valid email";
        }

        if(errorMsg != null){
            model.addAttribute("errorMessage", errorMsg);
            return "registration";
        }

        User newUser = User.builder()
                .username(username)
                .password(password)
                .firstName(first)
                .lastName(last)
                .email(email)
                .isAdmin(false)
                .build();

        UserDao userDao = new UserDaoImpl("database.properties");
        boolean registered = userDao.register(newUser);
        if(registered){
            String success = "Registration successful";
            model.addAttribute("message", success);
            return "index";
        }else{
            log.info("Could not register user with username: " + username + " and email: " + email +".");
            String failed = "Username/email address unavailable.";
            model.addAttribute("errorMessage", failed);
            return "registration";
        }
    }
}
