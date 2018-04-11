package com.baibei.accountservice.paycenter.bussiness;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baibei.accountservice.Bootstrap;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
@WebAppConfiguration
public class DailyPaycenterBusinessTest {

	@Autowired
    private DailyPaycenterBusiness dailyPaycenterBusiness;
	
	@Test
	public void testGetData4AssemblyMessage() {
		fail("Not yet implemented");
	}

}
