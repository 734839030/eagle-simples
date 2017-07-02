package com.seezoon.eagle.job.demo;

import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.seezoon.eagle.job.core.BaseJob;

/**
 * 简单 分片job  通过ShardingParameter 来做分片任务
 * @author hdf
 *
 */
@Component
public class DemoSharedingSimpleJobHandler extends BaseJob implements SimpleJob{
	@Override
	public void execute(ShardingContext shardingContext) {
		switch (shardingContext.getShardingItem()) {
        case 0: 
        	logger.debug("DemoSharedingSimpleJobHandler running .....shardingItem =" + shardingContext.getShardingItem() + "ShardingParameter="+shardingContext.getShardingParameter());
            break;
        case 1: 
        	logger.debug("DemoSharedingSimpleJobHandler running .....shardingItem =" + shardingContext.getShardingItem() + "ShardingParameter="+shardingContext.getShardingParameter());
            break;
		}
	}
}
