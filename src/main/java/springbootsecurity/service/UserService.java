package springbootsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootsecurity.dao.UserDao;
import springbootsecurity.model.Role;
import springbootsecurity.model.User;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToaAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToaAuthorities(Collection<Role> roles) {
        return roles.
                stream().
                map(r -> new SimpleGrantedAuthority(r.getName())).
                collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Transactional
    public void removeUser(long id) {
        userDao.deleteById(id);
    }

    @Transactional
    public void updateUser(User user) {
        userDao.save(user);
    }

    @Transactional
    public User getUser(long id) {
        return userDao.getById(id);
    }

    @Transactional
    public User getUserByName(String name) {
        return userDao.findByUsername(name);
    }
}