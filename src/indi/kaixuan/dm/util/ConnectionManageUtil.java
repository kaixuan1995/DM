package indi.kaixuan.dm.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionManageUtil {
	private static BoneCP connectionPool = null;
	private static Connection connection = null;
	private static PropertiesUtil prop = new PropertiesUtil("BoneCP");

	/**
	 * 关闭数据库连接池
	 */
	public static void closeBoneCp(){
		connectionPool.close();
	}
	
	/**
	 * 获取数据库链接
	 * @return
	 */
	public static Connection getConnection() {
		try {
			// 加载JDBC驱动
			Class.forName(prop.getMyProperty("DriverClass"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 设置连接池配置信息
			BoneCPConfig config = new BoneCPConfig();
			// 数据库的JDBC URL
			config.setJdbcUrl(prop.getMyProperty("URL"));
			// 数据库用户名
			config.setUsername(prop.getMyProperty("User"));
			// 数据库用户密码
			config.setPassword(prop.getMyProperty("Password"));
			// 数据库连接池的最小连接数
			config.setMinConnectionsPerPartition(5);
			// 数据库连接池的最大连接数
			config.setMaxConnectionsPerPartition(10);
			//
			config.setPartitionCount(1);
			// 设置数据库连接池
			connectionPool = new BoneCP(config);
			// 从数据库连接池获取一个数据库连接
			connection = connectionPool.getConnection(); // fetch a connection
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;

	}

	public void closeConnection() {
		connectionPool.shutdown();
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
