package com.seezoon.eagle.conf.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.seezoon.eagle.conf.demo.service.DemoService;
import com.seezoon.eagle.core.service.BaseService;

/**
 * 文件变化时候callback
 * 
 * @author hdf
 *
 */
@Service
@DisconfUpdateService(confFileKeys = {"demo/reload-demo.properties"})
public class DemoConfiCallback extends BaseService implements IDisconfUpdate{

	@Autowired
	private DemoService demoService;
	@Override
	public void reload() throws Exception {
		logger.info("i am reload ....");
		demoService.print();
	}

}
