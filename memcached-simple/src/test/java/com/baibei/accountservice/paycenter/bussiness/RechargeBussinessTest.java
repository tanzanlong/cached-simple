package com.baibei.accountservice.paycenter.bussiness;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baibei.account.dto.request.TransferRequest;
import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.paycenter.bussiness.ch.RechargeBussiness;
import com.baibei.accountservice.paycenter.exception.PasswordException;
import com.baibei.accountservice.paycenter.exception.PayException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
@WebAppConfiguration
public class RechargeBussinessTest {
    @Autowired
    private RechargeBussiness rechargeBussiness;
    @Test
    public void test() throws PayException, PasswordException{
        TransferRequest rechargeRequest=new TransferRequest();
        rechargeRequest.setAmount(2L);
        rechargeRequest.setPassword("123456789");
        rechargeRequest.setSerialNo("123456789");
        rechargeRequest.setUserId("201706120012");
        rechargeRequest.setOrgId("123");
        rechargeBussiness.rechargeRequest(rechargeRequest);
    }
    
    
    @Test
    public void testQueryRechargeStatus(){
        String orderId="75270c8e31324e3492c4ce12f4a405c2";
        rechargeBussiness.queryRechargeStatus(orderId);
    }
}
