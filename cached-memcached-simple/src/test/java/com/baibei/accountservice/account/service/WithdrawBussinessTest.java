package com.baibei.accountservice.account.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.paycenter.bussiness.ch.WithdrawBussiness;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
@WebAppConfiguration
public class WithdrawBussinessTest {
    @Autowired
    private WithdrawBussiness withdrawBussiness;
    @Test
    public void testCreatePersonalAccount(){
        String userId="322775965468069888";
        System.out.println(withdrawBussiness.queryCanWithdrawAmount(userId));
    }
    
}
