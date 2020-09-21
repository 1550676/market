package ru.zakharova.elena.market;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.zakharova.elena.market.entities.Role;
import ru.zakharova.elena.market.repositories.RolesRepository;
import ru.zakharova.elena.market.services.RolesService;
@ActiveProfiles("test")

@SpringBootTest
public class SpyTest {
    @Autowired
    private RolesService rolesService;

    @Spy
    private RolesRepository rolesRepository;

    @Test
    public void spyTest() {
        rolesRepository.save(new Role(1L, "admin"));
        rolesRepository.save(new Role(2L, "user"));
        rolesRepository.save(new Role(3L, "manager"));
        System.out.println(rolesRepository.findAll().toString());
        Role role = rolesService.findByName("admin");
        Assertions.assertEquals(1, role.getId());

    }
}
