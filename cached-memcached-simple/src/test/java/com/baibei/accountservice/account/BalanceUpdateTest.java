package com.baibei.accountservice.account;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.account.business.AccountBusiness;
import com.baibei.accountservice.account.vo.AccountBalanceModifyReq;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
@WebAppConfiguration
public class BalanceUpdateTest {

    @Autowired
    AccountBusiness accountBusiness;
    
    @Test
    public void testUpdateBalance() throws Exception{
        AccountBalanceModifyReq req = new AccountBalanceModifyReq();
        req.setBusinessType("WINE");
        req.setOrderType("1");
        req.setOrderId(UUID.randomUUID().toString());
        List<AccountBalanceModifyReq.Detail> detailList = new ArrayList<AccountBalanceModifyReq.Detail>();
        req.setDetailList(detailList);
        for(int i=0; i<2; i++){
            AccountBalanceModifyReq.Detail detail = new AccountBalanceModifyReq.Detail();
            detail.setAccountId(9009L + i);
            if(i == 0){
                detail.setAmount(-10000L);
            }else{
                detail.setAmount(10000L);
            }
            detail.setFeeItem("1");
            detail.setUserId("dummy_" + i);
            detail.setBalanceType("AVALIABLE");
            detailList.add(detail);
            detail.setOrgId("");
        }
        accountBusiness.modifyBalance(req);
    }
}
