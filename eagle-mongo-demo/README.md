## eagle-mongo 的例子

```
具体使用见src/test/java
```

例子使用eagle-mongo 封装的DAO,采用spring-data-mongo 中提供的MongoTemplate，分页工具类采用开源PageHelper

```
public abstract class MongoBaseDao<T> {
	
	protected  Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected MongoTemplate mongoTemplate;

	/**

	 * 保存或更新一个对象，

	 * @param t

	 * @return

	 */
	public void saveOrUpdate(T t) {
		this.mongoTemplate.save(t);
	}
	
	/**

	 * 保存一个对象

	 * @param t

	 */
	public void insert(T t){
		this.mongoTemplate.insert(t);
	}

	/**

	 * 根据Id从Collection中查询对象

	 * @return

	 */
	public T queryById(String id) {
		Query query = new Query();
		Criteria criteria = Criteria.where("_id").is(id);
		query.addCriteria(criteria);
		return this.mongoTemplate.findOne(query, this.getEntityClass());
	}

	/**

	 * 根据条件查询集合

	 * @param query 查询条件

	 * @return 满足条件的集合

	 */
	public List<T> queryList(Query query) {
		return this.mongoTemplate.find(query, this.getEntityClass());
	}

	/**

	 * 通过条件查询单个实体，有一个结果的话取第一个

	 * @param query

	 * @return

	 */
	public T queryOne(Query query) {
		return this.mongoTemplate.findOne(query, this.getEntityClass());
	}

	

	/**

	 * 根据条件查询库中符合记录的总数,为分页查询服务

	 * @param query 查询条件

	 * @return 满足条件的记录总数

	 */
	public Long getPageCount(Query query) {
		return this.mongoTemplate.count(query, this.getEntityClass());
	}
	/**

	 * 通过条件进行分页查询

	 * @param query 查询条件

	 * @param pageNum 查询起始值 ，

	 * @param pageSize  查询大小 ，类似mysql查询中的 limit start, size 中的 size

	 * @return 满足条件的集合

	 */
	public PageInfo<T> getPage(Query query, int pageNum,int pageSize){
		Page<T> page = new Page<T>(pageNum, pageSize);
		query.skip(page.getStartRow());
		query.limit(page.getPageSize());
		List<T> lists = this.mongoTemplate.find(query, this.getEntityClass());
		PageInfo<T> pageInfo = new PageInfo<T>(lists);
		pageInfo.setTotal(this.getPageCount(query));
		return pageInfo;
	}
	/**

	 * 根据Id删除用户

	 * @param id

	 */
	 public void deleteById(String id) {
		 Criteria criteria = Criteria.where("_id").in(id);
		 if(null!=criteria){
			 Query query = new Query(criteria);
			 if(null!=query && this.queryOne(query)!=null){
				 this.mongoTemplate.remove(query, this.getEntityClass());
			 }
		 }
	 }
	 
	 /**

	  * 根据条件删除

	  * @param query

	  */
	 public void delete(Query query){
		 this.mongoTemplate.remove(query, this.getEntityClass());
	 }

	/**

	 * 删除对象

	 * @param t

	 */
	 public void delete(T t){
		 this.mongoTemplate.remove(t);
	 }

	/**

	 * 更新满足条件的第一个记录

	 * @param query

	 * @param update

	 */
	 public void updateFirst(Query query,Update update){
		 this.mongoTemplate.updateFirst(query, update, this.getEntityClass());
	 }

	/**

	 * 更新满足条件的所有记录

	 * @param query

	 * @param update

	 */
	 public void updateMulti(Query query, Update update){
		 this.mongoTemplate.updateMulti(query, update, this.getEntityClass());
	 }

	/**

	 * 查找更新,如果没有找到符合的记录,则将更新的记录插入库中

	 * @param query

	 * @param update

	 */
	 public void updateInser(Query query, Update update){
		 this.mongoTemplate.upsert(query, update, this.getEntityClass());
	 }

	/**

	 * 钩子方法,由子类实现返回反射对象的类型

	 * @return

	 */
	protected  Class<T> getEntityClass(){
		return GenericsUtils.getSuperClassGenricType(this.getClass());
	};
```

