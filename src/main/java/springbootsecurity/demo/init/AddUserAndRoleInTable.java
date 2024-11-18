package springbootsecurity.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springbootsecurity.demo.model.Role;
import springbootsecurity.demo.model.User;
import springbootsecurity.demo.repositories.RoleRepository;
import springbootsecurity.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class AddUserAndRoleInTable {

    private final RoleRepository roleDao;
    private final UserService userService;

    @Autowired
    public AddUserAndRoleInTable(RoleRepository roleDao, UserService userService) {
        this.roleDao = roleDao;
        this.userService = userService;
    }

    @PostConstruct
    private void init() {
        roleDao.save(new Role(1L, "ROLE_ADMIN"));
        roleDao.save(new Role(2L, "ROLE_USER"));
        List<Role> adminRole = roleDao.findById(1L).stream().toList();
        List<Role> userRole = roleDao.findById(2L).stream().toList();
        userService.saveUser(new User("Ilnaz", "Rustanov", 25,
                                "admin@mail.ru", "admin", adminRole));

        userService.saveUser(new User("Tom", "Cat", 20,
                                "user@gmail.com", "user", userRole));
    }
}
