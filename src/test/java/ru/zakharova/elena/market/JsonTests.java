package ru.zakharova.elena.market;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.zakharova.elena.market.entities.Role;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class JsonTests {
    @Autowired
    private JacksonTester<Role> jackson;

    @Test
    public void jsonSerializationTest() throws Exception {
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        System.out.println(this.jackson.write(role).getJson());
        assertThat(this.jackson.write(role)).hasJsonPathNumberValue("$.id");
        assertThat(this.jackson.write(role)).extractingJsonPathStringValue("$.name").isEqualTo("USER");
    }

    @Test
    public void jsonDeserializationTest() throws Exception {
        String content = "{\"id\": 2,\"name\":\"ADMIN\"}";
        Role realRole = new Role();
        realRole.setId(2L);
        realRole.setName("ADMIN");

        assertThat(this.jackson.parse(content)).isEqualTo(realRole);
        assertThat(this.jackson.parseObject(content).getName()).isEqualTo("ADMIN");
    }
}