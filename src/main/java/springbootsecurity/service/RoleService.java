package springbootsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootsecurity.dao.RoleDao;
import springbootsecurity.model.Role;

import java.util.List;

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
}
