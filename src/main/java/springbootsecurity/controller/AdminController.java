package springbootsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springbootsecurity.model.Role;
import springbootsecurity.model.User;
import springbootsecurity.service.RoleService;
import springbootsecurity.service.UserService;

import java.util.HashSet;
import java.util.List;

@Controller
public class AdminController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(PasswordEncoder passwordEncoder, UserService userService, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String getUsers(@AuthenticationPrincipal UserDetails currentUser, ModelMap model) {
        User user = userService.getUserByEmail(currentUser.getUsername());
        model.addAttribute(user);
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        model.addAttribute("roles", roleService.getAllRole());
        return "tableUsers";
    }

    @GetMapping(value = "/admin/info")
    public String getUser(@AuthenticationPrincipal UserDetails currentUser, ModelMap model) {
        User user = userService.getUserByEmail(currentUser.getUsername());
        model.addAttribute(user);
        return "admin-info";
    }

    @PostMapping(value = "/admin")
    public String deleteUserDataBase(@RequestParam("id") long id){
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/addUser")
    public String pageUserForm(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        User user = userService.getUserByEmail(currentUser.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllRole());
        return "addUser";
    }

    @PostMapping("/admin/addUser")
    public String addUserDataBase(@ModelAttribute("newUser") User newUser, @RequestParam("roles") List<Long> roleIds) {
        List<Role> roles = roleService.getRolesByIds(roleIds);
        newUser.setRoles(new HashSet<>(roles));
        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty()) {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        userService.saveUser(newUser);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/updateUser")
    public String updateUserDataBase(@RequestParam("id") long id, @ModelAttribute User user, @RequestParam("roles") List<Long> roleIds) {
        User existingUser = userService.getUser(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        List<Role> rolesToAdd = roleService.getRolesByIds(roleIds);
        existingUser.getRoles().clear();
        for (Role role : rolesToAdd) {
            if (!existingUser.getRoles().contains(role)) {
                existingUser.addRole(role);
            }
        }
        userService.updateUser(existingUser);
        return "redirect:/admin";
    }
}
