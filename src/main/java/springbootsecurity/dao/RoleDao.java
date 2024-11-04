package springbootsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootsecurity.model.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
}
