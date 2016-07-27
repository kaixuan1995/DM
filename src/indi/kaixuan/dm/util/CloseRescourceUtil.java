package indi.kaixuan.dm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 关闭JDBC使用过程中的资源
 * @author Administrator
 *
 */
public class CloseRescourceUtil {
	public void safelyClose(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				//
			}
		}
	}

	public void safelyClose(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				//
			}
		}
	}

	public void safelyClose(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				//
			}
		}
	}
}
