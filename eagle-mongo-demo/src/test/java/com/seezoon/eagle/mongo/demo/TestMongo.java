package com.seezoon.eagle.mongo.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.seezoon.eagle.core.test.BaseJunitTest;
import com.seezoon.eagle.mongo.demo.dao.ChildDao;
import com.seezoon.eagle.mongo.demo.dao.PersonDao;
import com.seezoon.eagle.mongo.demo.entity.Child;
import com.seezoon.eagle.mongo.demo.entity.Person;

public class TestMongo extends BaseJunitTest{

	@Autowired
	private PersonDao personDao;
	@Autowired
	private ChildDao childDao;
	/**
	 * 插入
	 */
	@Test
	public void t1(){
		for (int i = 2;i<100;i++) {
			Child child = new Child("" + i, "黄登峰" + i, i, new Date(), true);
			List<Child> list = new ArrayList<Child>();
			list.add(child);
			Person person = new Person("" + i, "黄登峰" + i, i, new Date(), true, list);
			personDao.insert(person);
			childDao.insert(child);
			
		}
	}
	/**
	 * saveOrUdate
	 */
	@Test
	public void t2(){
		Person person = new Person("1", "黄登峰2", 12, new Date(), true, null);
		personDao.saveOrUpdate(person);
	}
	/**
	 * query by id
	 */
	@Test
	public void t3(){
		Person person = personDao.queryById("2");
		System.out.println(person);
	}
	/**
	 * queryList
	 */
	@Test
	public void t4(){
		/**
		 * 无限制组合
		 */
		Criteria criteria1 = Criteria.where("name").is("黄登峰2");
		Criteria criteria2 = Criteria.where("age").lt(100);
		Criteria criteria3 = Criteria.where("birth").lt(new Date());
		criteria3=	criteria1.orOperator(criteria2.andOperator(criteria3));
		Query query = new Query(criteria3);
		// 排序
		Direction direction=Direction.ASC;
		query.with(new Sort(direction, "name"));//可以添加多个字段
		query.with(new Sort(new Order(direction,"name",null)));
		List<Person> list = personDao.queryList(query);
		logger.debug(JSON.toJSONString(list));
	}
	/**
	 * delete by id
	 */
	@Test
	public void t5(){
		personDao.deleteById("2");
	}
	@Test
	public void t6(){
		/**
		 * 无限制组合
		 */
		Criteria criteria1 = Criteria.where("name").is("黄登峰2");
		Criteria criteria2 = Criteria.where("age").lt(100);
		criteria1 =criteria1.orOperator(criteria2);
		Query query = new Query(criteria1);
		// 排序
		Direction direction=Direction.ASC;
		query.with(new Sort(direction, "name"));//可以添加多个字段
		query.with(new Sort(new Order(direction,"name",null)));
		personDao.updateFirst(query, Update.update("age",1));
	}
	
	@Test
	public void t7(){
		Query query = new Query();
		PageInfo<Child> page = childDao.getPage(query, 1, 10);
		logger.debug(JSON.toJSONString(page));
	}
}
