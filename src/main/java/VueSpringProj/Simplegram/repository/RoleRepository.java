package VueSpringProj.Simplegram.repository;

import VueSpringProj.Simplegram.models.ERole;
import VueSpringProj.Simplegram.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//найти роль в списке ролей
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}