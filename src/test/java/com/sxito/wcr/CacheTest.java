package com.sxito.wcr;

import com.sxito.wcr.service.impl.DataCacheImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {


    @Autowired
    private DataCacheImpl dataCache;

    @Test
    public void contextLoads() {
        dataCache.put(3,"乱七八糟");
        dataCache.query(1);
        dataCache.query(1);
    }


}

