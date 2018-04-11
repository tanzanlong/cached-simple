package com.baibei.accountservice.paycenter.controller;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.paycenter.vo.DailySettlementNotify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Bootstrap.class)
@WebIntegrationTest({"server.port=5050", "management.port=1010"})
public class DailySettlementNotifyControllerTest {
    // private RestTemplate restTemplate;
    @Value("${local.server.port}")
    protected int port;
    protected URL base;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/account/dailySettlementNotify");
        // template = new TestRestTemplate();
    }
    
    @Test
    public void testDoRechargeTLH5() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        DailySettlementNotify dailySettlementNotify = new DailySettlementNotify();
        dailySettlementNotify.setFileName("BAT_TRADE_RESULT_CH_20170531.txt");
        dailySettlementNotify.setFileType("CH");
        dailySettlementNotify.fillSign("1234567890");
        HttpEntity<DailySettlementNotify> formEntity = new HttpEntity<DailySettlementNotify>(dailySettlementNotify, headers);
        String result =
                restTemplate.postForObject(String.format("%s/%s", base.toString(), "/receiveSettlementRes"),
                        formEntity,
                        String.class);
        System.out.println(result);
    }
    
    
}
