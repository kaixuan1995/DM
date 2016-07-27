package indi.kaixuan.dm.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlReadUtil {
	private SAXReader saxReader;
	private Document document;
	private static String filePath;

	public XmlReadUtil() {
		saxReader = new SAXReader();
		try {
			filePath = XmlReadUtil.class.getClassLoader()
					.getResource("dm-config.xml").getPath();
			document = saxReader.read(new File(filePath));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getSqlElement(String OperationMethod) {
		String sql = null;
		// 获取根元素
		Element root = document.getRootElement();
		for (Iterator<?> iter = root.elementIterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			for (Iterator<?> ziter = element.elementIterator(); ziter.hasNext();) {
				Element zElement = (Element) ziter.next();
				@SuppressWarnings("unchecked")
				List<Attribute> at = zElement.attributes();
				for(Attribute att:at){
					if(att.getData().equals(OperationMethod)){
						sql = zElement.getText();
					}
				}
				}

			}
		return sql;
		}

}
