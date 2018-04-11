package com.baibei.accountservice.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.account.service.ReportProviderImpl;
import com.baibei.accountservice.paycenter.dto.request.OrgAssertQuery;
import com.baibei.accountservice.paycenter.dto.response.OrgAssertDetail;
import com.baibei.accountservice.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Bootstrap.class)
@WebIntegrationTest({"server.port=5050", "management.port=1010"})
public class ReportProviderTest {

    @Autowired
    ReportProviderImpl reportProviderImpl;
    
    /*@Test
    public void testQueryBalanceSummary() throws Exception{
    	String userId = "323786882590838784";
    	Date startTime = new Date();
    	Date endTime = new Date();
    	
    	startTime.setMonth(5);
    	startTime.setDate(9);
    	
    	
    	endTime.setMonth(5);
    	endTime.setDate(12);
    	endTime.setHours(0);
    	endTime.setMinutes(0);
    	endTime.setSeconds(0);
    	
    	BalanceSummary balanceSummary = reportProviderImpl.queryBalanceSummary(userId, endTime, endTime);
    	org.junit.Assert.assertNotNull(balanceSummary);
    }*/
    
   /* @Test
    public void testQueryBalanceSumaryList() throws Exception{
    	String userId1 = "320209097129594880";
//    	String userId2 = "323786882590838784";
    	List<String> userIdList = new ArrayList<String>();
    	userIdList.add(userId1);
//    	userIdList.add(userId2);
    	
    	Date endTime = new Date();
    	endTime.setMonth(5);
    	endTime.setDate(12);
    	endTime.setHours(0);
    	endTime.setMinutes(0);
    	endTime.setSeconds(0);
    	List<BalanceSummary> balanceSummaryList = reportProviderImpl.queryBalanceSumaryList(userIdList, endTime, endTime);
    	org.junit.Assert.assertNotNull(balanceSummaryList);
//    	org.junit.Assert.assertEquals(2, balanceSummaryList.size());
    }*/
    

  /*  @Test
    public void testQueryIncome() throws Exception{
    	String userId = "320209097129594880";
    	Date endTime = new Date();
    	endTime.setMonth(5);
    	endTime.setDate(12);
    	endTime.setHours(0);
    	endTime.setMinutes(0);
    	endTime.setSeconds(0);
    	FeeAndInterest feeAndInterest = reportProviderImpl.queryIncome(userId, endTime, endTime);
    	org.junit.Assert.assertNotNull(feeAndInterest);
    }*/
    
/*    @Test
    public void testQueryBalanceSumaryListByOrgList() throws Exception{
    	String userId1 = "320209082185289728";
    	String userId2 = "1";
    	List<String> userIdList = new ArrayList<String>();
    	userIdList.add(userId1);
    	userIdList.add(userId2);
    	
    	Date endTime = new Date();
    	endTime.setMonth(5);
    	endTime.setDate(12);
    	endTime.setHours(0);
    	endTime.setMinutes(0);
    	endTime.setSeconds(0);
    	List<OrgBalanceSummary> balanceSummaryList = reportProviderImpl.queryBalanceSumaryListByOrgList(userIdList, endTime, endTime);
//    	org.junit.Assert.assertNotNull(balanceSummaryList);
    	org.junit.Assert.assertEquals(2, balanceSummaryList.size());
    }*/
    
    @Test
    public void testQueryBalanceSumaryListByOrgList() throws Exception{
        
        OrgAssertQuery orgAssertQuery=new OrgAssertQuery();
        
        List<String> orgIds=new ArrayList<String>();
        orgIds.add("326431932126728192"); //326431932126728192
        orgAssertQuery.setOrgIds(orgIds);
        
        String startTimeStr="20170501";
        String endTimeStr="20170623";
        
        Date startTime=DateUtil.parse(startTimeStr, DateUtil.FORMAT_6);
        Date endTime=DateUtil.parse(endTimeStr, DateUtil.FORMAT_6);
        Date now=new Date();
        if(startTime==null){
            startTime=now;
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);
        startTime=calendar.getTime();
        
        if(endTime==null){
            endTime=now;
        }
        Calendar endcalendar=Calendar.getInstance();
        endcalendar.setTime(endTime);
        endcalendar.set(Calendar.HOUR_OF_DAY, 23);
        endcalendar.set(Calendar.MINUTE, 59);
        endcalendar.set(Calendar.SECOND, 59);
        endcalendar.set(Calendar.MILLISECOND, 59);
        endTime=endcalendar.getTime();
        
        orgAssertQuery.setEndTime(endTime);
        orgAssertQuery.setStartTime(startTime);
        orgAssertQuery.setOrgIds(orgIds);
        
        Map<String, OrgAssertDetail> balanceSummaryList = reportProviderImpl.queryOrgAssert(orgAssertQuery);
//      org.junit.Assert.assertNotNull(balanceSummaryList);
   //     org.junit.Assert.assertEquals(2, balanceSummaryList.size());
        System.out.println(JSON.toJSONString(balanceSummaryList));
    }
}
