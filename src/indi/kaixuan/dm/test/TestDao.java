package indi.kaixuan.dm.test;

import indi.kaixuan.dm.dao.DaoSupport;
import indi.kaixuan.dm.dto.AdminGoodsInfo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class TestDao {

	private DaoSupport daoSupport = new DaoSupport();

	@Test
	public void testExecuteDTO() {
		List<Object> list = new ArrayList<Object>();
		list.add(2);
		List<AdminGoodsInfo> listInfo = new ArrayList<AdminGoodsInfo>();
		// daoSupport.executeDTO("addStudent", list);
		// daoSupport.executeDTO("deleteStudentByName", list);
		//Student stu  = daoSupport.queryOneDTO(Student.class,"queryStudentByname", list);
		//Office off = daoSupport.queryOneDTO(Office.class, "queryOffice", list);
		//System.out.println(off);
		listInfo = daoSupport.queryDTO(AdminGoodsInfo.class,"queryAdminGoodsInfo", null); 
		for(AdminGoodsInfo adminInfo:listInfo){
			System.out.println(adminInfo);
		}
		//Student obj = daoSupport.queryOneDTO(Student.class,"queryStudentByname", list);
		//System.out.println(obj);
		/*
		 * for(Object obj:list){ System.out.println(obj); }
		 */
	}

}
