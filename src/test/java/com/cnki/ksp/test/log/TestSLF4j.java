package com.cnki.ksp.test.log;

import java.util.Random;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSLF4j {
	
	@Test
	public void testInitSLF4j(){
		 String name = "hzh";
	        Logger logger = LoggerFactory.getLogger(TestSLF4j.class);
	        logger.debug("Hello World: " + new Random().nextInt());
	        logger.info("hello {}", name);
	        System.out.println("testInitSLF4j, ok");
	}

}
