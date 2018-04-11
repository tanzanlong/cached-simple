package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Test {
    public static void main(String[] args) throws Exception{
        String filleFileName ="/Users/peng/Downloads/BAT_BALANCE_REQ_CB_20170924.txt";
        File f = new File(filleFileName);
        BufferedReader bw = new BufferedReader(new FileReader(f));
        String str = bw.readLine();
        while(str != null){
            JSONObject jsonObj = JSON.parseObject(str);
            Long accountId = jsonObj.getLong("accountId");
            String userId = jsonObj.getString("userId");
            String businessType = jsonObj.getString("businessType");
            Long balance = jsonObj.getLong("balance");
            String sql = "update t_account_balance set amount = " + balance + " where account_id=" + accountId + " and user_id='" + userId + "' and business_type = '" + businessType + "' and balance_type = 'AVALIABLE';";
            System.out.println(sql);
            str = bw.readLine();
        }
    }
}
