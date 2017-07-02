package com.seezoon.eagle.job.demo;

import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.seezoon.eagle.job.core.BaseJob;

/**
 * 简单 不分片job  只会在一台服务器上执行 ，不是固定频率，如每1秒执行，作业跑了2秒，下次执行就是3 秒后，不是立即。
 * @author hdf
 *
 */
@Component
public class DemoNoSharedingSimpleJobHandler extends BaseJob implements SimpleJob{
	@Override
	public void execute(ShardingContext shardingContext) {
		logger.debug("DemoNoSharedingSimpleJobHandler running .....");
	}
}
