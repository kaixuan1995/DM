package indi.kaixuan.dm.test;

import indi.kaixuan.dm.util.XmlReadUtil;

import org.junit.Test;


public class TestSQl {

	@Test
	public void testSql() {
		XmlReadUtil xml = new XmlReadUtil();
		String sql = xml.getSqlElement("updateStudent1");
		System.out.println(sql);
	}

}
