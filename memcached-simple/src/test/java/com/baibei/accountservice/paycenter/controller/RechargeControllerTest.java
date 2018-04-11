package com.baibei.accountservice.paycenter.controller;

import java.net.URL;
import java.util.Date;

import lombok.Data;

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

import com.baibei.account.dto.request.QueryTransferRecordsRequest;
import com.baibei.account.dto.request.TransferRequest;
import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.paycenter.dto.RechargeRequest;
import com.baibei.accountservice.util.MD5;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Bootstrap.class)
@WebIntegrationTest({"server.port=5050", "management.port=1010"})
public class RechargeControllerTest {
    // private RestTemplate restTemplate;
    @Value("${local.server.port}")
    protected int port;
    protected URL base;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:8088/portal-special-h5/special/financial");
    }
    
    @Test
    public void testDoRecharge() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        RechargeRequest rechargeRequest = new RechargeRequest();
        rechargeRequest.setAmount(5L);
        rechargeRequest.setUserId("uid201705310006");
        rechargeRequest.setPassword("12345678");
        rechargeRequest.setSerialNo("");
        //rechargeRequest.setOrgId("org123");
        HttpEntity<RechargeRequest> formEntity = new HttpEntity<RechargeRequest>(rechargeRequest, headers);
        String result =
                restTemplate.postForObject(String.format("%s/%s", base.toString(), "/dorecharge"),
                        formEntity,
                        String.class);
        System.out.println(result);
    }
    
    
    
    
    @Test
    public void testAssert() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        QuerySpecialAssetDto rechargeRequest = new QuerySpecialAssetDto();
        rechargeRequest.setBusinessType("");
        //rechargeRequest.setEndTime();
        //rechargeRequest.setOrgId(orgId);
        //rechargeRequest.setStartTime(startTime);
        rechargeRequest.setUserId(326437292602429440L);
        //rechargeRequest.setOrgId("org123");
        HttpEntity<QuerySpecialAssetDto> formEntity = new HttpEntity<QuerySpecialAssetDto>(rechargeRequest, headers);
        String result =
                restTemplate.postForObject(String.format("%s/%s", base.toString(), "specialassert"),
                        formEntity,
                        String.class);
        System.out.println(result);
    }
    
    
    @Test
    public void testChargeInfo() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        QuerySpecialAssetDto rechargeRequest = new QuerySpecialAssetDto();
        rechargeRequest.setBusinessType("");
        //rechargeRequest.setEndTime();
        //rechargeRequest.setOrgId(orgId);
        //rechargeRequest.setStartTime(startTime);
        rechargeRequest.setUserId(326437292602429440L);
        //rechargeRequest.setOrgId("org123");
        HttpEntity<QuerySpecialAssetDto> formEntity = new HttpEntity<QuerySpecialAssetDto>(rechargeRequest, headers);
        String result =
                restTemplate.postForObject(String.format("%s/%s", base.toString(), "broker/chargeInfo"),
                        formEntity,
                        String.class);
        System.out.println(result);
    }
    
    
    @Test
    public void testCharge() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        TransferRequest rechargeRequest = new TransferRequest();
        rechargeRequest.setPassword("12345678");
        rechargeRequest.setAmount(7L);
        //rechargeRequest.setOrgId("org123");
        HttpEntity<TransferRequest> formEntity = new HttpEntity<TransferRequest>(rechargeRequest, headers);
        String result =
                restTemplate.postForObject(String.format("%s/%s", base.toString(), "broker/charge"),
                        formEntity,
                        String.class);
        System.out.println(result);
    }
    
    
    
    @Test
    public void testWithdrawInfo() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        QuerySpecialAssetDto rechargeRequest = new QuerySpecialAssetDto();
        rechargeRequest.setBusinessType("");
        //rechargeRequest.setEndTime();
        //rechargeRequest.setOrgId(orgId);
        //rechargeRequest.setStartTime(startTime);
        rechargeRequest.setUserId(326437292602429440L);
        //rechargeRequest.setOrgId("org123");
        HttpEntity<QuerySpecialAssetDto> formEntity = new HttpEntity<QuerySpecialAssetDto>(rechargeRequest, headers);
        String result =
                restTemplate.postForObject(String.format("%s/%s", base.toString(), "broker/withdrawInfo"),
                        formEntity,
                        String.class);
        System.out.println(result);
    }
    
    
    
    @Test
    public void testWithdraw() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        TransferRequest rechargeRequest = new TransferRequest();
        rechargeRequest.setPassword("12345678");
        rechargeRequest.setAmount(7L);
        //rechargeRequest.setOrgId("org123");
        HttpEntity<TransferRequest> formEntity = new HttpEntity<TransferRequest>(rechargeRequest, headers);
        String result =
                restTemplate.postForObject(String.format("%s/%s", base.toString(), "broker/withdraw"),
                        formEntity,
                        String.class);
        System.out.println(result);
    }
    
    
    @Test
    public void testLog() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        QueryTransferRecordsRequest rechargeRequest = new QueryTransferRecordsRequest();
        rechargeRequest.setCurrentPage(1);
        rechargeRequest.setPageSize(10);
        //rechargeRequest.setOrgId("org123");
        HttpEntity<QueryTransferRecordsRequest> formEntity = new HttpEntity<QueryTransferRecordsRequest>(rechargeRequest, headers);
        String result =
                restTemplate.postForObject(String.format("%s/%s", base.toString(), "broker/chargeWithdrawDetails"),
                        formEntity,
                        String.class);
        System.out.println(result);
    }
    
    
   @Data
   static class QuerySpecialAssetDto {
        private Date startTime;
        private Date endTime;
        private Long orgId;
        private Long userId;
        private String businessType;
    }
   
   public static void main(String[] args) {
       System.out.println( MD5.sign("12345678", "54e83ca8-18b9-4fca-80cf-c8b1ac3210fb", "UTF-8"));
}
}
