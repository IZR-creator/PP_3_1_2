package springbootsecurity.demo.service;


import springbootsecurity.demo.model.User;

import java.util.List;

public interface UserService {

    public List<User> findAllUsers();

    public User findUserById(Long userId);

    public void saveUser(User user);

    public boolean updateUser(Long id, User user);

    public boolean deleteUser(Long userId);

    User findByEmail(String email);
}
