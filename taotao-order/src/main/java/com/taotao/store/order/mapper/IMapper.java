package com.taotao.store.order.mapper;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.taotao.store.order.bean.Where;

public interface IMapper<T> {

	/**
	 * 按照分页查询
	 * 
	 * @param bounds
	 * @return
	 */
	public PageList<T> queryList(PageBounds bounds);

	/**
	 * 按照where条件分页查询
	 * 
	 * @param bounds
	 * @param where
	 * @return
	 */
	public PageList<T> queryListByWhere(PageBounds bounds, @Param("where")Where where);

	/**
	 * 通过ID查询数据
	 * 
	 * @param id
	 * @return
	 */
	public T queryByID(@Param("id") String id);

	/**
	 * 通过where条件查询
	 * 
	 * @param where
	 * @return
	 */
	public T queryByWhere(@Param("where") Where where);
	
	/**
	 * 新增数据
	 * 
	 * @param t
	 */
	public void save(T t);

	/**
	 * 更新数据
	 * 
	 * @param t
	 */
	public void update(T t);

	/**
	 * 通过ID删除数据
	 * 
	 * @param id
	 * @return 返回受影响的条数
	 */
	public Integer deleteByID(@Param("id") Long id);

	/**
	 * 通过IDs删除数据
	 * 
	 * @param ids
	 * @return 返回受影响的条数
	 */
	public Integer deleteByIDS(@Param("ids") Long[] ids);

}
