package indi.kaixuan.dm.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 查询某个properties的属性文件信息
 * @author bluedot
 *
 */
public class PropertiesUtil {
	private Properties prop = new Properties();

	public PropertiesUtil(String properName){
		String properPath = "./src/" + properName + ".properties";
		try {
			FileInputStream fis = new FileInputStream(properPath);
			try {
				prop.load(fis);// 将属性文件流装载到Properties对象中
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis.close();// 关闭流
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取属性值,如果没有那就新建，value为默认
	 * 
	 * @param key
	 * @return
	 */
	public String getMyProperty(String... key) {
		if (key.length == 1)
			return prop.getProperty(key[0]);
		else {
			return prop.getProperty(key[0], key[1]);
		}
	}

	/**
	 * 新增、修改sitename的属性值
	 * 
	 * @param key
	 * @param value
	 */
	public void setMyProperties(String key, String value, String properName) {
		prop.setProperty(key, value);
		saveProperties(properName);
	}

	/**
	 * 存储properties属性列表
	 */
	private void saveProperties(String properName) {
		// 文件输出流
		String properPath = "./src/" + properName + ".properties";
		try {
			FileOutputStream fos = new FileOutputStream(properPath);

			// 将Properties集合保存到流中
			try {
				prop.store(fos, "Copyright (c) Summer Make");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// 关闭流
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
