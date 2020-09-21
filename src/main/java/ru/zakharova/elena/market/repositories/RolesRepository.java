package ru.zakharova.elena.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zakharova.elena.market.entities.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
	Role findOneByName(String name);
}
