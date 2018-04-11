package com.baibei.accountservice.settlement;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.settlement.business.DailyBalanceBusiness;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Bootstrap.class)
@WebIntegrationTest({"server.port=5050", "management.port=1010"})
public class DailyBalanceBusinessTest {
    @Autowired
    private DailyBalanceBusiness dailyBalanceBusiness;
  
    @Test
    public void testGetData4AssemblyMessage(){
    	//String businessType = "WINE";
    	List<String> balanceTypeList = new ArrayList<String>();
    	balanceTypeList.add("AVALIABLE");
    	balanceTypeList.add("FROZEN");
    	
    	try {
			dailyBalanceBusiness.getData4AssemblyMessage("CH", null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	/*for(TAccountBalance tAccountBalance:tAccountBalanceList){
    		System.out.println(tAccountBalance.getAmount());
    	}*/
//    	org.junit.Assert.assertNotNull(tAccountBalanceList);
    	
    }
}
