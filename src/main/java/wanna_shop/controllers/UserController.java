package wanna_shop.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
                           Model model, HttpSession session){
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
            String success = "Registration successful, you are now logged in.";
            model.addAttribute("message", success);
            User loggedInUser = userDao.login(username, password);
            session.setAttribute("currentUser", loggedInUser);
            return "index";
        }else{
            log.info("Could not register user with username: " + username + " and email: " + email +".");
            String failed = "Username/email address unavailable.";
            model.addAttribute("errorMessage", failed);
            return "registration";
        }
    }

    @PostMapping("login")
    public String login(@RequestParam(name="username") String username,
                        @RequestParam(name="password") String password,
                        Model model, HttpSession session){
        String errorMsg = null;
        if(username == null || username.isBlank()){
            errorMsg = "Cannot register without a username";
        }
        else if(password == null || password.isBlank()){
            errorMsg = "Cannot register without a password";
        }
        if(errorMsg != null){
            model.addAttribute("errorMessage", errorMsg);
            return "login";
        }

        UserDao userDao = new UserDaoImpl("database.properties");
        User loggedInUser = userDao.login(username, password);
        if(loggedInUser != null){
            String success = "Login successful";
            model.addAttribute("message", success);
            session.setAttribute("currentUser", loggedInUser);
            return "index";
        }else{
            String failed = "Username/password incorrect.";
            model.addAttribute("errorMessage", failed);
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session){
        session.setAttribute("currentUser", null);

        model.addAttribute("message", "Logout successful.");
        return "index";
    }
}
