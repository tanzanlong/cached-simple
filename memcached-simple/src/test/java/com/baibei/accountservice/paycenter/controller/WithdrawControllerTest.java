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
import com.baibei.accountservice.paycenter.dto.WithdrawRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Bootstrap.class)
@WebIntegrationTest({"server.port=5050", "management.port=1010"})
public class WithdrawControllerTest {
    // private RestTemplate restTemplate;
    @Value("${local.server.port}")
    protected int port;
    protected URL base;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/account/withdraw");
    }
    
    @Test
    public void testDoWithdraw() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        WithdrawRequest rechargeRequest = new WithdrawRequest();
        rechargeRequest.setAmount(6L);
        rechargeRequest.setUserId("326437292602429440");
        rechargeRequest.setPassword("12345678");
        rechargeRequest.setSerialNo("");
       // rechargeRequest.setOrgId("org123");
        HttpEntity<WithdrawRequest> formEntity = new HttpEntity<WithdrawRequest>(rechargeRequest, headers);
        String result =
                restTemplate.postForObject(String.format("%s/%s", base.toString(), "/dowithdraw"),
                        formEntity,
                        String.class);
        System.out.println(result);
    }
    
    
}
