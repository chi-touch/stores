package org.chi;

import org.chi.repositories.UserRepository;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RepositoryTest {

    @Test
    public void testDattaConnection(){
        try(Connection connection = UserRepository.connect()){
           assertNotNull(connection);
            System.out.println("connection ->"+connection);
        }catch (SQLException ex){
            assertNull(ex);
            ex.printStackTrace();
        }
    }

}
