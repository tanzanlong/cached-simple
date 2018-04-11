package com.baibei.accountservice.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baibei.account.dto.request.CreatePersonalAccountRequest;
import com.baibei.account.provider.AccountProvider;
import com.baibei.accountservice.Bootstrap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Bootstrap.class)
@WebIntegrationTest({"server.port=5050", "management.port=1010"})
public class AccountProviderTest {


    @Autowired
    AccountProvider accountProviderImpl;
    
    @Test
    public void testUpdateBalance() throws Exception{
        CreatePersonalAccountRequest request=new CreatePersonalAccountRequest();
        request.setBankCard("test3");
        request.setBankCode("test3");
        request.setIdCode("test3");
        request.setMobile("test3");
        request.setName("test3");
        request.setPassword("123456");
        request.setTopOrgId("test3");
        request.setUserId("uid2017060610002");
        accountProviderImpl.createPersonalAccount(request);
    }

}
