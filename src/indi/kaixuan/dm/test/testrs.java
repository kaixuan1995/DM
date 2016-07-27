package indi.kaixuan.dm.test;

import indi.kaixuan.dm.util.ConnectionManageUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class testrs {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
        Connection conn = ConnectionManageUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement("select *from customer");
        ResultSet rs = ps.executeQuery();
        List<Object> retList = new ArrayList<Object>();
        while(rs.next()){
        	System.out.println(rs.getObject(1));
        
        ResultSetMetaData meta = rs.getMetaData();
        System.out.println(meta.getColumnCount());
        
        Map<Object,Object> recordMap = new HashMap<Object,Object>();
        for(int i=1;i<=meta.getColumnCount();i++){
        	 String name = meta.getColumnName(i);
             // column value
             Object value = rs.getObject(i);
             recordMap.put(name, value);
             System.out.println(name+":"+value);
        }
        retList.add(recordMap);}
        System.out.println(retList);
	}

}
