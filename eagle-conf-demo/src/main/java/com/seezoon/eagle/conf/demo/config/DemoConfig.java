package com.seezoon.eagle.conf.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 一般会在代码中会变得单独写在一个类中便于维护
 * 
 * @author hdf
 */
@Component
public class DemoConfig {

	@Value("${reload.name}")
	private String rname;

	@Value("${noreload.name}")
	private String nrname;

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getNrname() {
		return nrname;
	}

	public void setNrname(String nrname) {
		this.nrname = nrname;
	}
	
}
