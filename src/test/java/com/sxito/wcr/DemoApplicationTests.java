package com.sxito.wcr;

import com.sxito.wcr.service.ServiceTest;
import com.sxito.wcr.utils.CommonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class DemoApplicationTests {


	@Qualifier("serviceTestImpl")
	@Autowired
	private ServiceTest serviceTestl;

	@Autowired
	private CommonUtil commonUtil;

	@Test
	public void contextLoads() {
		serviceTestl.sayHello();

	}


}
