package springbootsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springbootsecurity.model.Role;
import springbootsecurity.model.User;
import springbootsecurity.service.RoleService;
import springbootsecurity.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String getUsers(ModelMap model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "tableUsers";
    }

    @PostMapping(value = "/admin")
    public String deleteUserDataBase(@RequestParam("id") long id){
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/addUser")
    public String addUser(ModelMap model) {
        User user = new User();
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "addAndUpdateUser";
    }

    @PostMapping(value = "/admin/addUser")
    public String addUserDataBase(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        List<Role> roles = roleService.getRolesByIds(roleIds);
        user.setRoles(new HashSet<>(roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/updateUser")
    public String updateUser(@RequestParam("id") long id, ModelMap model) {
        User user = userService.getUser(id);
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "addAndUpdateUser";
    }

    @PostMapping(value = "/admin/updateUser")
    public String updateUserDataBase(@ModelAttribute("user") User user, @RequestParam("roles") List<Long> roleIds) {
        User existingUser = userService.getUser(user.getId());

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }

        List<Role> rolesToAdd = roleService.getRolesByIds(roleIds);

        for (Role role : rolesToAdd) {
            if (!existingUser.getRoles().contains(role)) {
                existingUser.addRole(role);
            }
        }

        userService.updateUser(existingUser);
        return "redirect:/admin";
    }
}
