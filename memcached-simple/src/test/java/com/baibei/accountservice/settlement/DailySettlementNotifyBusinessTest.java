package com.baibei.accountservice.settlement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.settlement.business.DailySettlementNotifyBusiness;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Bootstrap.class)
@WebIntegrationTest({"server.port=5050", "management.port=1010"})
public class DailySettlementNotifyBusinessTest {
    @Autowired
    private DailySettlementNotifyBusiness dailySettlementNotifyBusiness;
    
    @Test
    public void testGetData4AssemblyMessage() throws Exception{
    	
    	
    	String fileName = "BAT_BALANCE_RESULT_CH_20170607.txt";
    	String fileType = "1";
    	
    	dailySettlementNotifyBusiness.dealSettlementRes(fileName, fileType);
    }
}
