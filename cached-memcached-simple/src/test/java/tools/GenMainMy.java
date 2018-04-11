package tools;

/**
 * 生产数据操作层代码
 * 
 * @author hbm
 *
 */
public class GenMainMy {
	public static void main(String[] args) {
		String configFile = "/generatorConfig.xml";
		try {
            // t_wine_channel_request_log t_authority_config
		   
            String[] tableNames = new String[] { 
                    "t_recharge_withdraw_feeitem"
//                    "t_account",
//                    "t_account_balance",
//                    "t_account_balance_ontheway",
//                    "t_account_cashier_log",
//                    "t_account_password",
//                    "t_customer",
//                    "t_four_element",
//                    "t_pay_limit",
//                    "t_recharge_order",
//                    "t_withdraw_order"
                    };
			GenMybatisFiles.gen(configFile, tableNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
