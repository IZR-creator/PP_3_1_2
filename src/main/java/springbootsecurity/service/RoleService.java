package springbootsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootsecurity.dao.RoleDao;
import springbootsecurity.model.Role;

import java.util.List;

@Service
public class RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public List<Role> getAllRole() {
        return roleDao.findAll();
    }

    @Transactional
    public void saveRole(Role role) {
        roleDao.save(role);
    }

    @Transactional
    public void removeRole(long id) {
        roleDao.deleteById(id);
    }

    @Transactional
    public void updateRole(Role user) {
        roleDao.save(user);
    }

    @Transactional
    public Role getRole(long id) {
        return roleDao.getById(id);
    }

    @Transactional
    public List<Role> getRolesByIds(List<Long> ids) {
        return roleDao.findAllById(ids);
    }
}
