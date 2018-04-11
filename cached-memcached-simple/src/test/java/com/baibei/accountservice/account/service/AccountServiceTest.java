package com.baibei.accountservice.account.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.baibei.account.dto.request.CreateOrgAccountRequest;
import com.baibei.account.dto.request.CreatePersonalAccountRequest;
import com.baibei.account.exception.AccountException;
import com.baibei.account.provider.AccountProvider;
import com.baibei.accountservice.Bootstrap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Bootstrap.class)
@WebAppConfiguration
public class AccountServiceTest {
    @Autowired
    private AccountProvider accountProviderImpl;
    @Test
    public void testCreatePersonalAccount(){ //321289636536258560
        CreatePersonalAccountRequest customerRegInfo=new CreatePersonalAccountRequest();
        customerRegInfo.setUserId("327123666301227008");
        customerRegInfo.setBankCard("542222412412");
        customerRegInfo.setBankCode("1234");
        //customerRegInfo.setBusinessType("CH");
        customerRegInfo.setIdCode("4413632017061402222");
       // customerRegInfo.setIdType("1");
        customerRegInfo.setName("tanzl");
        customerRegInfo.setMobile("13113980029");
        customerRegInfo.setTopOrgId("1");
        customerRegInfo.setPassword("123456789");
        try {
            System.out.println(JSON.toJSONString(accountProviderImpl.createPersonalAccount(customerRegInfo)));
        } catch (AccountException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testCreateOrgAccount(){ //321289636536258560
        CreateOrgAccountRequest customerRegInfo=new CreateOrgAccountRequest();
        customerRegInfo.setUserId("2000");
        //customerRegInfo.setBusinessType("CH");
        customerRegInfo.setIdCode("441363201706143333");
       // customerRegInfo.setIdType("1");
        customerRegInfo.setName("tanzl");
        customerRegInfo.setMobile("13113980029");
        customerRegInfo.setOrgType("CENTER");
        try {
            System.out.println(JSON.toJSONString(accountProviderImpl.createOrgAccount(customerRegInfo)));
        } catch (AccountException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    

    /**
     * 销户 
     */
    @Test
    public void testDeleteAccount() { // 321289636536258560
        String userId = "201706120011";
        try {
            System.out.println(JSON.toJSONString(accountProviderImpl.deleteAccount(userId)));
        } catch (AccountException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    /**
     * 修改密码
     */
    @Test
    public void testModifyPassword() { // 321289636536258560
        String userId = "201706120011";
        try {
            System.out.println(JSON.toJSONString(accountProviderImpl.modifyPassword(userId, "012345678", "012345678")));
        } catch (AccountException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    /**
     * 修改密码
     */
    @Test
    public void testResetPassword() { // 321289636536258560
        String userId = "201706120011";
        try {
            System.out.println(JSON.toJSONString(accountProviderImpl.resetPassword(userId, "012345678")));
        } catch (AccountException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    /**
     * 修改密码
     */
    @Test
    public void testResetPasswordBySms() { // 321289636536258560
        String userId = "201706120011";
        try {
            System.out.println(JSON.toJSONString(accountProviderImpl.resetPasswordBySms(userId,"")));
        } catch (AccountException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
