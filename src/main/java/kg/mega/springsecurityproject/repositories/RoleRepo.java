package kg.mega.springsecurityproject.repositories;

import kg.mega.springsecurityproject.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
