package springbootsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springbootsecurity.model.User;
import springbootsecurity.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String getUser(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = ((UserDetails) auth.getPrincipal()).getUsername();
        User user = userService.getUserByName(currentUsername);
        if (!user.getUsername().equals(currentUsername)) {
            throw new AccessDeniedException("You are not authorized to view this user");
        }
        model.addAttribute(user);
        return "user-info";
    }
}
