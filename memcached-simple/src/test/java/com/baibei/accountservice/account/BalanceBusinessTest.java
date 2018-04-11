package com.baibei.accountservice.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.account.business.BalanceBusiness;
import com.baibei.accountservice.paycenter.dto.request.OrgFeeDetailQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Bootstrap.class)
@WebIntegrationTest({"server.port=5050", "management.port=1010"})
public class BalanceBusinessTest {
    @Autowired
    BalanceBusiness balanceBusiness;
    
    @Test
    public void testFreeze() throws Exception{
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //calendar.set(Calendar., 1);
        System.out.println(calendar.getTime());
        //System.out.println(balanceBusiness.queryFeeBy("19881010", calendar.getTime(),new Date(),Constants.FEE_TYPE_LOAN_POUNDAGE,"uid201705270003"));
    }
    
    @Test
    public void testQueryOrgFeeDetail() throws Exception{
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //calendar.set(Calendar., 1);
        System.out.println(calendar.getTime());
        OrgFeeDetailQuery orgFeeDetailQuery=new OrgFeeDetailQuery();
        List<String> userIds=new ArrayList<String>();
        userIds.add("uid201705270002");
        orgFeeDetailQuery.setStartDate(calendar.getTime());
        orgFeeDetailQuery.setEndDate(new Date());
        //orgFeeDetailQuery.setUserIds(userIds);
        System.out.println(balanceBusiness.queryOrgFeeDetail(orgFeeDetailQuery));
    }
}
