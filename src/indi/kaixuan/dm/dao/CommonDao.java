package indi.kaixuan.dm.dao;

import indi.kaixuan.dm.dao.exception.DaoException;
import indi.kaixuan.dm.util.CloseRescourceUtil;
import indi.kaixuan.dm.util.ConnectionManageUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 基础Dao,提供对数据库的CURD
 * @author Administrator
 *
 */
public class CommonDao {
	private Connection conn;
	private CloseRescourceUtil closeRescourceUtil;

	public CommonDao() {
		conn = ConnectionManageUtil.getConnection();
		closeRescourceUtil = new CloseRescourceUtil();
	}

	/**
	 * 开启事物
	 * 
	 * @throws DaoException
	 */
	public void begin() throws DaoException {
		if (conn != null) {
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				throw new DaoException("can not begin transaction", e);
			}
		} else {
			throw new DaoException("connection not opened!");
		}
	}

	/**
	 * 提交事务
	 * 
	 * @throws DaoException
	 */
	public void commit() throws DaoException {
		try {
			if (conn != null && !conn.getAutoCommit()) {
				conn.commit();
				conn.setAutoCommit(true);
			} else {
				if (conn == null) {
					throw new DaoException("connection not opened!");
				} else {
					throw new DaoException("first begin then commit please!");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("can not commit transaction!", e);
		}
	}

	/**
	 * 事物回滚
	 * 
	 * @throws DaoException
	 */
	public void rollback() throws DaoException {
		try {
			if (conn != null && !conn.getAutoCommit()) {
				conn.rollback();
				conn.setAutoCommit(true);
			} else {
				if (conn == null) {
					throw new DaoException("connection not opened!");
				} else {
					throw new DaoException("first begin then rollback please!");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("can not rollback transaction!", e);
		}
	}

	/**
	 * 将查询到的结果集ResultSet转化为泛型T
	 * 
	 * @param cla
	 *            返回结果的类型
	 * @param rs
	 * @return
	 * @throws DaoException
	 */
	private <T> List<T> convert(Class<T> cla, ResultSet rs) throws DaoException {

		// record list
		List<T> retList = new ArrayList<T>();

		try {
			ResultSetMetaData meta = rs.getMetaData();

			// column count
			int colCount = meta.getColumnCount();
			// each record
			while (rs.next()) {
				T t = null;
				try {
					t = cla.newInstance();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Map<Object, Object> recordMap = new HashMap<Object, Object>();

				// each column
				for (int i = 1; i <= colCount; i++) {
					// column name
					String name = meta.getColumnName(i);

					// column value
					Object value = rs.getObject(i);
					// add column to record, like [id:1,name:kai,addr:beijing]
					recordMap.put(name, value);
				}

				Iterator it = recordMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Entry) it.next();
					convertToDTO(cla, t, entry);
				}
				retList.add(t);
				// ad record to list
			}
		} catch (SQLException ex) {
			throw new DaoException("can not convert result set to list of map",
					ex);
		}
		return retList;
	}

	/**
	 * 将一个Map.Entry中的值映射到DTO中
	 * 
	 * @param cla
	 *            DTO的类类型
	 * @param t
	 *            DTO的类型
	 * @param entry
	 *            Map.Entry
	 */
	private <T> void convertToDTO(Class<T> cla, T t,
			Map.Entry<Object, Object> entry) {
		String fieldName = (String) entry.getKey();
		Field field;
		try {
			field = cla.getDeclaredField(fieldName);
			if (field != null) {
				field.setAccessible(true);
				try {
					/*System.out.println(field.getName()+"："+field.getType().getName());
					if(entry.getValue()==null && field.getType().getName()==Integer.class.toString()){
						field.set(t,0);
					}*/
					field.set(t, entry.getValue());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 由于使用的是PreparedStatement，遇到占位符时拼接sql语句
	 * 
	 * @param pstmt
	 * @param params
	 * @throws DaoException
	 */
	private void apply(PreparedStatement pstmt, List<Object> params)
			throws DaoException {
		try {
			// if params exist
			if (params != null && params.size() > 0) {
				// parameters iterator
				@SuppressWarnings("rawtypes")
				Iterator it = params.iterator();

				// parameter index
				int index = 1;
				while (it.hasNext()) {

					Object obj = it.next();
					// if null set ""
					if (obj == null) {
						pstmt.setObject(index, "");
					} else {
						// else set object
						pstmt.setObject(index, obj);
					}

					// next index
					index++;
				}
			}
		} catch (SQLException ex) {
			throw new DaoException("can not apply parameter", ex);
		}
	}

	/**
	 * 查询方法
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public <T> List<T> query(Class<T> cla, String sql, List<Object> params)
			throws DaoException {
		List<T> result = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			System.out.println("SQL语句：" + sql);
			this.apply(pstmt, params);
			rs = pstmt.executeQuery();
			result = this.convert(cla, rs);
		} catch (SQLException ex) {
			throw new DaoException("can not execute query", ex);
		} finally {
			closeRescourceUtil.safelyClose(rs);
			closeRescourceUtil.safelyClose(pstmt);
		}

		return result;
	}

	/**
	 * 查询单个值
	 * 
	 * @param cla
	 * @param sql
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public <T> T queryOne(Class<T> cla, String sql, List<Object> params)
			throws DaoException {
		List<T> list = this.query(cla, sql, params);
		if (list == null || list.size() == 0) {
			throw new DaoException("data not exist");
		} else {
			T t = (T) list.get(0);
			return t;
		}
	}

	/**
	 * 更新、插入、删除
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws DaoException
	 */
	public int execute(String sql, List<Object> params) throws DaoException {
		int ret = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			this.apply(pstmt, params);
			ret = pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new DaoException("", ex);
		} finally {
			closeRescourceUtil.safelyClose(pstmt);
		}

		return ret;
	}

	/**
	 * 批处理方法（查询）一次查询多条SQL语句
	 * 
	 * @param sqlArray
	 * @param paramArray
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T>[] queryBatch(Class<T> cla, String[] sqlArray,
			List<Object>[] paramArray) throws DaoException {
		List<T> rets = new ArrayList<T>();
		if (sqlArray.length != paramArray.length) {
			throw new DaoException("sql size not equal parameter size");
		} else {
			for (int i = 0; i < sqlArray.length; i++) {
				String sql = sqlArray[i];
				List<Object> param = paramArray[i];
				List<T> ret = this.query(cla, sql, param);
				rets.add((T) ret);
			}
			return (List<T>[]) rets.toArray();
		}
	}

	/**
	 * 批处理方法（更新）
	 * 
	 * @param sqlArray
	 * @param paramArray
	 * @return
	 * @throws DaoException
	 */
	public int[] executeBatch(String[] sqlArray, List<Object>[] paramArray)
			throws DaoException {
		List<Object> rets = new ArrayList<Object>();
		if (sqlArray.length != paramArray.length) {
			throw new DaoException("sql size not equal parameter size");
		} else {
			for (int i = 0; i < sqlArray.length; i++) {
				int ret = this.execute(sqlArray[i], paramArray[i]);
				rets.add(new Integer(ret));
			}

			int[] retArray = new int[rets.size()];
			for (int i = 0; i < retArray.length; i++) {
				retArray[i] = ((Integer) rets.get(i)).intValue();
			}

			return retArray;
		}
	}

	/**
	 * 释放connection
	 * 
	 * @throws DaoException
	 */
	public void close() throws DaoException {
		try {
			if (conn != null && conn.getAutoCommit()) {
				conn.close();
			} else {
				if (conn == null) {
					throw new DaoException(
							"can not close null connection, first new then close");
				} else {
					throw new DaoException(
							"transaction is running, rollback or commit befor close please.");
				}
			}
		} catch (SQLException ex) {
			throw new DaoException("Can not close common dao");
		}
	}

}
