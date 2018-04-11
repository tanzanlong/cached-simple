package com.baibei.accountservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SystemInitializing implements InitializingBean {

	static Logger logger = LoggerFactory.getLogger(SystemInitializing.class);
	
    private ExecutorService executorService;
    
    public void afterPropertiesSet() throws Exception {
        doInit();
    }

    private void doInit() {
        try {
        	executorService = Executors.newFixedThreadPool(2);
            logger.info("Start schedulerMasterCheck ...");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("error:{}", e.getMessage());
        }
        
        
//        executorService.execute(marketAndGoodInfoListener);
//        logger.info("Start marketAndGoodInfoListenerThread ...");
//
        logger.info("Start autoCloseSuccessListener ...");
    }

}
