package com.baibei.accountservice.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baibei.account.dto.request.FreezeBalanceRequest;
import com.baibei.accountservice.Bootstrap;
import com.baibei.accountservice.account.service.BalanceProviderImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Bootstrap.class)
public class BalanceProviderTest {

    @Autowired
    BalanceProviderImpl balanceProviderImpl;
    
//    @Test
//    public void testFreeze() throws Exception{
//        FreezeBalanceRequest request = new FreezeBalanceRequest();
//        request.setAmount(10000L);
//        request.setEntrustNum(UUID.randomUUID().toString());
//        request.setOrgId("");
//        request.setUserId("uid201705270002");
//        balanceProviderImpl.freezeBalance(request);
//    }
    
//    @Test
//    public void testUnFreeze() throws Exception{
//        UnfreezeBalanceRequest request = new UnfreezeBalanceRequest();
//        request.setAmount(10000L);
//        request.setEntrustNum(UUID.randomUUID().toString());
//        request.setOrgId("");
//        request.setUserId("uid201705270002");
//        balanceProviderImpl.unfreezeBalance(request);
//    }
    
//    @Test
//    public void testSettleMatched() throws Exception{
//        SettleMatchedRequest request = new SettleMatchedRequest();
//        request.setAmount(15364L);
//        request.setBueryOrgId("326431932126728192");
//        request.setBuyerId("328863275532029952");
//        request.setSellerId("326437292602429440");
//        request.setSellerOrgId("326437286793318400");
//        request.setTradeNum(UUID.randomUUID().toString());
//        SettleMatchedRequest.FeeItem feeItem = new SettleMatchedRequest.FeeItem();
//        feeItem.setFee(-31L);
//        feeItem.setOrgId("100");
//        feeItem.setType(0);
//        feeItem.setUserId("328863275532029952");
//        
//        SettleMatchedRequest.FeeItem feeItem2 = new SettleMatchedRequest.FeeItem();
//        feeItem2.setFee(31L);
//        feeItem2.setOrgId("100");
//        feeItem2.setType(1);
//        feeItem2.setUserId("1");
//        List<SettleMatchedRequest.FeeItem> list = new ArrayList<SettleMatchedRequest.FeeItem>();
//        list.add(feeItem);
//        list.add(feeItem2);
//        request.setFeeList(list);
//        balanceProviderImpl.settleMatched(request);
//    }
    
//    @Test
//    public void testSettleLoanFund() throws Exception{
//        SettleLoanFundRequest request = new SettleLoanFundRequest();
//        request.setAmount(1000000L);
//        request.setBorrowId("uid2017060610001");
//        request.setBorrowOrgId("");
//        request.setLenderId("uid2017060610002");
//        request.setLenderOrgId("");
//        //request.setOrderNum(UUID.randomUUID().toString());
//        request.setOrderNum("df5d22f6-7c66-4c18-ac5a-eb8c096cc368");
//        List<SettleLoanFundRequest.FeeItem> list = new ArrayList<SettleLoanFundRequest.FeeItem>();
//        SettleLoanFundRequest.FeeItem feeItem = new SettleLoanFundRequest.FeeItem();
//        feeItem.setFee(-500L);
//        feeItem.setOrgId("");
//        feeItem.setUserId("uid2017060610002");
//        SettleLoanFundRequest.FeeItem feeItem2 = new SettleLoanFundRequest.FeeItem();
//        feeItem2.setFee(500L);
//        feeItem2.setOrgId("");
//        feeItem2.setUserId("uid2017060610002");
//        list.add(feeItem);
//        list.add(feeItem2);
//        request.setFeeList(list);
//        balanceProviderImpl.settleLoanFund(request);
//    }
//    
//    
//    @Test
//    public void testSettleLoanSpotFee() throws Exception{
//        SettleLoanSpotFeeRequest request = new SettleLoanSpotFeeRequest();
//        request.setBorrowId("uid201705270002");
//        request.setOrderNum(UUID.randomUUID().toString());
//        List<SettleLoanSpotFeeRequest.FeeItem> list = new ArrayList<SettleLoanSpotFeeRequest.FeeItem>();
//        SettleLoanSpotFeeRequest.FeeItem feeItem = new SettleLoanSpotFeeRequest.FeeItem();
//        feeItem.setFee(-300L);
//        feeItem.setOrgId("");
//        feeItem.setUserId("uid201705270002");
//        SettleLoanSpotFeeRequest.FeeItem feeItem2 = new SettleLoanSpotFeeRequest.FeeItem();
//        feeItem2.setFee(300L);
//        feeItem2.setOrgId("");
//        feeItem2.setUserId("uid201705270003");
//        list.add(feeItem);
//        list.add(feeItem2);
//        request.setFeeList(list);
//        balanceProviderImpl.settleLoanSpotFee(request);
//    }
    
//    @Test
//    public void testSettleRepayment() throws Exception{
//        SettleRepaymentRequest request = new SettleRepaymentRequest();
//      request.setAmount(1000000L);
//      request.setBorrowId("uid201705270002");
//      request.setBorrowOrgId("");
//      request.setLenderId("uid201705270005");
//      request.setLenderOrgId("");
//      request.setOrderNum(UUID.randomUUID().toString());
//      balanceProviderImpl.settleRepayment(request);
//    }
    
//    @Test
//    public void testSettleLoanInterest() throws Exception{
//      SettleLoanInterestRequest request = new SettleLoanInterestRequest();
//      request.setBorrowId("uid201705270002");
//      request.setOrderNum(UUID.randomUUID().toString());
//      List<SettleLoanInterestRequest.FeeItem> list = new ArrayList<SettleLoanInterestRequest.FeeItem>();
//      SettleLoanInterestRequest.FeeItem feeItem = new SettleLoanInterestRequest.FeeItem();
//      feeItem.setFee(-200L);
//      feeItem.setOrgId("");
//      feeItem.setUserId("uid201705270002");
//      SettleLoanInterestRequest.FeeItem feeItem2 = new SettleLoanInterestRequest.FeeItem();
//      feeItem2.setFee(200L);
//      feeItem2.setOrgId("");
//      feeItem2.setUserId("uid201705270003");
//      list.add(feeItem);
//      list.add(feeItem2);
//      request.setInterestList(list);
//      balanceProviderImpl.settleLoanInterest(request);
//    }
    
//    @Test
//    public void testDelivery() throws Exception{
//        DeliveryRequest request = new DeliveryRequest();
//        request.setInvoicerId("uid201705270003");
//        request.setInvoicerIncomeAmount(6000L);
//        request.setInvoicerOrgId("");
//        request.setOrdererId("uid201705270002");
//        request.setOrderNum(UUID.randomUUID().toString());
//        request.setOrdererOrgId("");
//        request.setOrdererIncomeAmount(-6000L);
//        balanceProviderImpl.delivery(request);
//      }
    
//    @Test
//    public void testTradeOrderRequest() throws Exception{
//        TradeOrderRequest request = new TradeOrderRequest();
//        request.setInvoicerId("uid201705270003");
//        request.setInvoicerOrgId("");
//        request.setOrdererId("uid201705270002");
//        request.setOrderNum(UUID.randomUUID().toString());
//        request.setOrdererOrgId("");
//        request.setAmount(40000L);
//        balanceProviderImpl.tradeOrder(request);
//      }
    
//  @Test
//  public void testLossSettleMatched() throws Exception{
//      LossSettleMatchedRequest request = new LossSettleMatchedRequest();
//      
//      request.setAdvancedAmount(5400L);
//      request.setBuyerPayAmount(5600L);
//      request.setAdvancedUserId("321247756830248960");
//      request.setAdvancedOrgId("");
//      
//      request.setAmount(10000L);
//      request.setBueryOrgId("");
//      request.setBuyerId("320296935749718016");
//      request.setSellerId("1");
//      request.setSellerOrgId("");
//      request.setTradeNum(UUID.randomUUID().toString());
//      
//      LossSettleMatchedRequest.FeeItem feeItem = new LossSettleMatchedRequest.FeeItem();
//      feeItem.setFee(-1000L);
//      feeItem.setOrgId("");
//      feeItem.setType(1);
//      feeItem.setUserId("320296935749718016");
//      
//      LossSettleMatchedRequest.FeeItem feeItem2 = new LossSettleMatchedRequest.FeeItem();
//      feeItem2.setFee(1000L);
//      feeItem2.setOrgId("");
//      feeItem2.setType(1);
//      feeItem2.setUserId("2");
//      List<LossSettleMatchedRequest.FeeItem> list = new ArrayList<LossSettleMatchedRequest.FeeItem>();
//      list.add(feeItem);
//      list.add(feeItem2);
//      request.setFeeList(list);
//      balanceProviderImpl.lossSettleMatched(request);
//  }
    
/*  @Test
  public void testQueryBalance() throws Exception{
      String userId = "200";
      Balance balance = balanceProviderImpl.queryBalance(userId);
//      org.junit.Assert.assertNotNull(balance);
      org.junit.Assert.assertEquals(8995973L, balance.getAvailableBalance().longValue());
  }*/
  
/*  @Test
  public void testQueryBalanceAndSignedStatus() throws Exception{
      String userId = "200";
      BalanceAndSignedStatus balanceAndSignedStatus = balanceProviderImpl.queryBalanceAndSignedStatus(userId);
//      org.junit.Assert.assertNotNull(balance);
      org.junit.Assert.assertEquals(8995973L, balanceAndSignedStatus.getBalance().getAvailableBalance().longValue());
  }*/
//  @Test
//  public void testQueryBalanceList() throws Exception{
//	  List<String> userIds = new ArrayList<>();
////	  Boolean ignoreZeroAmount = true;
//	  Boolean ignoreZeroAmount = false;
//	  String userId = "200";
//	  String userId2 = "1001";
//	  userIds.add(userId);
//	  userIds.add(userId2);
//	  List<BalanceAndSignedStatus> balanceAndSignedStatus = balanceProviderImpl.queryBalanceList(userIds,ignoreZeroAmount);
//      org.junit.Assert.assertNotNull(balanceAndSignedStatus);
////	  org.junit.Assert.assertEquals(8995973L, balanceAndSignedStatus.getBalance().getAvailableBalance().longValue());
//  }
    
//    @Test
//    public void testQryTransStatus() throws Exception{
//        QryTransStatusRequest request = new QryTransStatusRequest();
//        request.setTransNum("17060818024177301360");
//        QryTransStatusResponse response = balanceProviderImpl.qryTransStatus(request);
//        System.out.println(response);
//    }
//    
    @Test
    public void testRollback() throws Exception{
        FreezeBalanceRequest request = new FreezeBalanceRequest();
        request.setEntrustNum("17070510144210700459");
        request.setAmount(36580L);
        request.setOrgId("");
        request.setUserId("329705876841697280");
        balanceProviderImpl.rollbackFreezeBalance(request);
    }
}
