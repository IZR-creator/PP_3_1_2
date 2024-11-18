package springbootsecurity.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootsecurity.demo.model.User;
import springbootsecurity.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userDao;

    @Autowired
    public UserServiceImpl(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    @Transactional
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userDao.findById(userId);
        return userFromDb.orElse(new User());
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(user.getRoles());
        userDao.save(user);
    }

    @Override
    @Transactional
    public boolean updateUser(Long id, User updatedUser) {
        Optional<User> userFromDb = userDao.findById(id);
        if (userFromDb.isPresent()) {
            User existingUser = userFromDb.get();

            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setRoles(updatedUser.getRoles());

            if (updatedUser.getPassword() != null
                    && !updatedUser.getPassword().isEmpty()
                    && !passwordEncoder.matches(updatedUser.getPassword(), existingUser.getPassword())) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            userDao.save(existingUser);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId) {
        if (userDao.findById(userId).isPresent()) {
            userDao.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}

