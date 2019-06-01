package education.controller;

import education.config.JWTConfig;
import education.entity.User;
import education.request.UserLoginRequest;
import education.request.UserRegisterRequest;
import education.response.Response;
import education.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestBody UserRegisterRequest registerRequest, HttpServletResponse response) throws Exception {
        User user = userService.findUserByUsername(registerRequest.getUsername());

        if (user != null) {
            return new Response("The username is already used", false);
        } else {
            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
            newUser.setEmail(registerRequest.getEmail());
            newUser.setPhone(registerRequest.getPhone());

            int result = userService.insert(newUser);
            System.out.println(result);
            JWTConfig jwt = new JWTConfig();
            String  token = jwt.createToken(newUser.getUsername());
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);

            Cookie cookie1 = new Cookie("name",newUser.getUsername());
            response.addCookie(cookie1);
            return new Response("yes", true);
        }

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.findUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        if (user != null) {
            JWTConfig jwt = new JWTConfig();
            String token = jwt.createToken(user.getUsername());
            Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);

            Cookie cookie1 = new Cookie("name", user.getUsername());
            response.addCookie(cookie1);
            return new Response("token", true);
        } else {
            return new Response(null, false);
        }
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request, @CookieValue(value = "token", required = false) String cookie,
                             @CookieValue(value = "name", required = false) String name, Model model){
        System.out.println(cookie);
        if (cookie == null){
            return "login";
        }
        JWTConfig jwt = new JWTConfig();

        List<User> users = userService.findAllUsers();
        for (User user:
             users) {
            System.out.println(user.getUsername());
        }
        if (jwt.verifyToken(cookie, name)){
            model.addAttribute("users",users);
            return "home";
        }
        else{
            return "login";
        }
    }
}
