package com.example.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)


@DataJpaTest
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class EmployeeRepositoryTest {
    @Autowired
    EmployeeRepository repository;

    @Test
    public void testRepo(){
        assertNotNull(repository.findAll());
        assertEquals(2,repository.findAll().size());
        assertEquals("Bratislava",repository.findAll().get(0).getFirstName());
    }
}