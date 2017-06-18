## eagle-mybatis 的例子

```
具体使用见src/test/java
```

主要使用eagel-mybatis 提供的CrudService，分页工具类采用开源PageHelper

```
/**
 * 当需要自动拥有增删改查功能时候继承
 * @author hdf
 *
 */
@Transactional(rollbackFor=Exception.class)
public abstract class CrudService<D extends CrudDao<T>,T extends BaseEntity>{

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected D dao; 
	
	public int insert(T t){
		return dao.insert(t);
	}
	public int insertSelective(T t){
		return dao.insert(t);
	}
	public int updateByPrimaryKeySelective(T t){
		return dao.updateByPrimaryKeySelective(t);
	}
	public int updateByPrimaryKey(T t){
		return dao.updateByPrimaryKey(t);
	}
	@Transactional(readOnly=true)
	public T selectByPrimaryKey(Serializable id){
		return dao.selectByPrimaryKey(id);
	}
	public int deleteByPrimaryKey(Serializable id){
		return dao.deleteByPrimaryKey(id);
	}
	@Transactional(readOnly=true)
	public List<T> findList(T t){
		return dao.findList(t);
	}
	@Transactional(readOnly=true)
	public PageInfo<T> findByPage(T t,int pageNum,int pageSize,boolean count){
		PageHelper.startPage(pageNum, pageSize, count);
		List<T>  list = this.findList(t);
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		return pageInfo;
	}
	@Transactional(readOnly=true)
	public PageInfo<T> findByPage(T t,int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize, Boolean.TRUE);
		List<T>  list = this.findList(t);
		PageInfo<T> pageInfo = new PageInfo<T>(list);
		return pageInfo;
	}
}
```