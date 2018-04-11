package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 本地按文件预演结算
 * @author peng
 *
 */
public class SettlementLocalTest {
    public static void main(String[] args) throws Exception{
        //上一日业务系统余额文件
        String filleFileName ="/Users/peng/Downloads/BAT_BALANCE_REQ_CB_20170924.txt";
        //当日结算文件
        String settlementFileName = "/Users/peng/Downloads/BAT_TRADE_CB_20170925.txt";
        File f = new File(filleFileName);
        BufferedReader bw = new BufferedReader(new FileReader(f));
        String str = bw.readLine();
        Map<Long, Long> accountId2Amount = new HashMap<Long, Long>();
        while(str != null){
            JSONObject jsonObj = JSON.parseObject(str);
            Long accountId = jsonObj.getLong("accountId");
            String userId = jsonObj.getString("userId");
            String businessType = jsonObj.getString("businessType");
            Long balance = jsonObj.getLong("balance");
            accountId2Amount.put(accountId, balance);
            str = bw.readLine();
        }
        
        
        File f2 = new File(settlementFileName);
        BufferedReader bw2 = new BufferedReader(new FileReader(f2));
        String str2 = bw2.readLine();
        Map<Long, Long> accountId2SettlementAmount = new HashMap<Long, Long>();
        while(str2 != null){
            JSONObject jsonObj = JSON.parseObject(str2);
            JSONArray jsonArray = jsonObj.getJSONArray("items");
            for(int i=0; i<jsonArray.size(); i++){
                String s = jsonArray.get(i).toString();
                JSONObject itemObj = JSON.parseObject(s);
                Long accountId = itemObj.getLong("accountId");
                Long amount = itemObj.getLong("amount");
                
                if(accountId.longValue() == 360139902472871936L){
                    System.out.println(accountId + " " + amount);
                }
                Long oldSettlementAmount = accountId2SettlementAmount.get(accountId);
                if(oldSettlementAmount == null){
                    oldSettlementAmount = 0L;
                }
                oldSettlementAmount += amount;
                accountId2SettlementAmount.put(accountId, oldSettlementAmount);
            }
            str2 = bw2.readLine();
        }
        
        Set<Long> accountIdSet = accountId2Amount.keySet();
        for(Long accountId : accountIdSet){
            Long oldAmount = accountId2Amount.get(accountId);
            Long settlementAmount = accountId2SettlementAmount.get(accountId);
            if(settlementAmount != null){
                Long newAmount = oldAmount + settlementAmount;
                accountId2Amount.put(accountId, oldAmount);
                //System.out.println("账户ID:" + accountId + " 期初:" + oldAmount + " 期末：" + newAmount + " 变动值：" + settlementAmount);
                if(newAmount < 0){
                    System.err.println("出错了");;
                }
            }
        }
        
        accountIdSet = accountId2SettlementAmount.keySet();
        for(Long accountId : accountIdSet){
            Long settlementAmount = accountId2SettlementAmount.get(accountId);
            Long amount = accountId2Amount.get(accountId);
            if(accountId.longValue() == 361824002648543232L)
            if(amount==null){
                System.out.println(accountId + " " + settlementAmount);
            }
        }
        
    }
}
