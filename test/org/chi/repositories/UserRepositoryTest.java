package org.chi.repositories;

import org.chi.models.User;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private final UserRepository userRep = new UserRepository();
    @Test
    void savedUserTest(){
        User user = new User();
        user.setWalletId(1L);
        User savedUser = userRep.saveUser(user);
        assertNotNull(savedUser);

    }

    @Test
    public void testFindUserById(){
       User user = userRep.findById(2L).orElseThrow();
       assertNotNull(user);
       assertEquals(2L,user.getId());
    }

    @Test
    public void testUpdateUser( ){
        Long userId = 2L;
        Long walletId = 200L;

        User user  = userRep.updateUser(userId, walletId);

        assertNotNull(user);

    assertEquals(200L,user.getWalletId());
    }


    @Test
    public void testDeleteUser(){
        userRep.deleteById(1L);
        Optional<User> user  = userRep.findById(1L);

        assertTrue(user.isEmpty());
    }

//    @Test
//    public void testFindAll(){
//        List<User> users = userRep.findAll();
//        assertNotNull(users);
//        assertEquals(3, users.size());
//    }
}