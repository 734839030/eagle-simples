package com.seezoon.eagle.dubbo.demo.dto;

import java.io.Serializable;

/**
 * 为了保持调用方兼容 默认统一serialVersionUID
 * fst kyro 序列化并不要求实现序列化接口
 * 服务方和调用方类变化的场景
 * @see http://www.cnblogs.com/guanghuiqq/archive/2012/07/18/2597036.html
 * @author hdf
 *
 */
public class CommonDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
