package ru.zakharova.elena.market.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.zakharova.elena.market.entities.Role;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTest {
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void roleRepositorySizeTest() {
        Role role = new Role();
        role.setName("ROLE_DIRECTOR");
        entityManager.persist(role);
        entityManager.flush();
        List<Role> roleList = rolesRepository.findAll();
        Assertions.assertEquals(4, roleList.size());
        Assertions.assertEquals("ROLE_CUSTOMER", roleList.get(0).getName());
    }

    @Test
    public void findOneRoleByNameTest() {
        List<Role> roleList = rolesRepository.findAll();
        Assertions.assertEquals(3, roleList.size());
        Role role = rolesRepository.findOneByName("ROLE_CUSTOMER");
        Assertions.assertEquals(1, role.getId());
    }
}
