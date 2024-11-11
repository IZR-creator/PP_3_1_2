package springbootsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootsecurity.dao.RoleDao;
import springbootsecurity.model.Role;
import springbootsecurity.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
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

    public List<Role> getRolesByIds(List<Long> ids) {
        return roleDao.findAllById(ids);
    }

    public void saveRole(Role role) {
        roleDao.save(role);
    }

    public Set<Role> findByIds(List<Long> roleIds) {
        return new HashSet<>(roleDao.findAllById(roleIds));
    }
}
