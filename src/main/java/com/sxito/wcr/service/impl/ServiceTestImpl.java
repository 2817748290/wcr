package com.sxito.wcr.service.impl;

import com.sxito.wcr.service.ServiceTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceTestImpl implements ServiceTest {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Override
    public void sayHello() {
        System.out.println("gg");
        String a = "abc";
        logger.info("gg{}",a);
    }
}
