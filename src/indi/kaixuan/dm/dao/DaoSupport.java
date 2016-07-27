package indi.kaixuan.dm.dao;

import indi.kaixuan.dm.dao.exception.DaoException;
import indi.kaixuan.dm.util.XmlReadUtil;

import java.util.List;

/**
 * 对CommDao的封装，真正对外提供Dao服务的类
 * @author Administrator
 *
 */
public class DaoSupport {
	private CommonDao commDao;
	private XmlReadUtil xmlReadUtil;

	public DaoSupport(){
		commDao = new CommonDao();
		xmlReadUtil = new XmlReadUtil();
	}
	/**
	 * 对外公开的增加、删除、修改方法
	 * @param operaMethod
	 * @param params
	 * @return
	 */
	public boolean executeDTO(String operaMethod, List<Object> params) {
		try {
			String sql = xmlReadUtil.getSqlElement(operaMethod);
			commDao.begin();
			commDao.execute(sql, params);
			commDao.commit();
		} catch (DaoException e) {
			try {
				commDao.rollback();
			} catch (DaoException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				commDao.close();
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}

		return true;
	}
	
	/**
	 * 对外公开的批量查询方法
	 * @param operaMethod
	 * @param params
	 * @return
	 */
	public boolean executeBatchDTO(String[] operaMethod,List<Object>[] params){
		for(int i=0;i<operaMethod.length;i++){
			for(int j=0;j<operaMethod.length;j++){
				String sql = xmlReadUtil.getSqlElement(operaMethod[i]);
				try {
					commDao.begin();
					commDao.execute(sql, params[j]);
					commDao.commit();
				} catch (DaoException e) {
					try {
						commDao.rollback();
					} catch (DaoException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}finally{
					try {
						commDao.close();
					} catch (DaoException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 对外公开的查询方法
	 * @param cla  要返回的查询结果 类型,用于将结果集映射到实体
	 * @param operaMethod  对应xml文件中的**Operation
	 * @param params sql语句中的占位符集合
	 * @return
	 */
	public <T> List<T> queryDTO(Class<T> cla,String operaMethod, List<Object> params){
        String sql = xmlReadUtil.getSqlElement(operaMethod);
		List<T> list = null;
		try {
			commDao.begin();
			list =  commDao.query(cla,sql, params);
			commDao.commit();
		} catch (DaoException e) {
			try {
				commDao.rollback();
			} catch (DaoException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				commDao.close();
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 对外公开的批量查询的方法
	 * @param operaMethod
	 * @param params
	 * @return
	 */
	public <T> List<T> queryBatchDTO(Class<T> cla,String[] operaMethod,List<Object>[] params){
		List<T> list = null;
		for(int i=0;i<operaMethod.length;i++){
			for(int j=0;j<operaMethod.length;j++){
				String sql = xmlReadUtil.getSqlElement(operaMethod[i]);
				try {
					commDao.begin();
					list = commDao.query(cla,sql, params[j]);
					commDao.commit();
				} catch (DaoException e) {
					try {
						commDao.rollback();
					} catch (DaoException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}finally{
					try {
						commDao.close();
					} catch (DaoException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 对外公开的查询结果为单一对象  的方法
	 * @param operaMethod
	 * @param params
	 * @return
	 */
	public <T> T queryOneDTO(Class<T> cla,String operaMethod,List<Object> params){
		String sql = xmlReadUtil.getSqlElement(operaMethod);
		if(sql == null){
			try {
				throw new DaoException("没有找到映射关系："+operaMethod);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		T t = null;
		try {
			commDao.begin();
			t =  commDao.queryOne(cla,sql, params);
			commDao.commit();
		} catch (DaoException e) {
			try {
				commDao.rollback();
			} catch (DaoException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				commDao.close();
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//TODO   关闭数据库连接问题
		}
		return t;
	}

}
