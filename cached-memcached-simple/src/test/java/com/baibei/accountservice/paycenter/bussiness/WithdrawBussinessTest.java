package com.baibei.accountservice.paycenter.bussiness;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baibei.account.dto.request.TransferRequest;
import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.paycenter.bussiness.ch.WithdrawBussiness;
import com.baibei.accountservice.paycenter.exception.PasswordException;
import com.baibei.accountservice.paycenter.exception.PayException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
@WebAppConfiguration
public class WithdrawBussinessTest {

    @Autowired
    private WithdrawBussiness withdrawBussiness;

    @Test
    public void test() throws PayException, PasswordException {
        TransferRequest withdrawRequest = new TransferRequest();
        withdrawRequest.setAmount(1L);
        withdrawRequest.setPassword("12345678");
        withdrawRequest.setSerialNo("");
        withdrawRequest.setUserId("326437292602429440");
        withdrawRequest.setOrgId("326437286793318400");
        withdrawBussiness.withdrawRequest(withdrawRequest);
    }
}
