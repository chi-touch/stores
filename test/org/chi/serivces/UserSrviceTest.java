package org.chi.serivces;

import org.chi.service.UserServices;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserSrviceTest {

    private final UserServices userService = new UserServices();
    @Test
    public void testTransferFunds(){
        Long senderId = 2L;
        Long recipientId = 4L;
        BigDecimal amount = new BigDecimal(500);
       String response =  userService.transferFunds(senderId,recipientId,amount);
       assertNotNull(response);

       assertEquals()
    }
}
