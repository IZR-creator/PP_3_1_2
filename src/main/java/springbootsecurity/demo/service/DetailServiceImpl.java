package springbootsecurity.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import springbootsecurity.demo.model.User;
import springbootsecurity.demo.repositories.UserRepository;

@Component
public class DetailServiceImpl implements UserDetailsService {

    private final UserRepository userDao;

    @Autowired
    public DetailServiceImpl(UserRepository userDao) {
        this.userDao = userDao;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return user;
    }
}
