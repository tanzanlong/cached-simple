package com.baibei.accountservice.account;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.baibei.account.dto.request.BalanceLogQuery;
import com.baibei.account.dto.response.BalanceLog;
import com.baibei.account.dto.response.PageResponse;
import com.baibei.account.provider.BalanceLogProvider;
import com.baibei.accountservice.Bootstrap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Bootstrap.class)
@WebIntegrationTest({"server.port=5050", "management.port=1010"})
public class BalanceLogProviderTest {
    @Autowired
    BalanceLogProvider balanceLogProvider;
    
    @Test
    public void testSearch() throws Exception{
        BalanceLogQuery balanceLogQuery=new BalanceLogQuery();
        balanceLogQuery.setCurrentPage(1);
        balanceLogQuery.setPageSize(10);
        balanceLogQuery.setUserId("dummy_1");
        PageResponse<List<BalanceLog>> page= balanceLogProvider.queryBalanceLog(balanceLogQuery);
        System.out.println(JSON.toJSONString(page));
    }
}
