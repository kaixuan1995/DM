package indi.kaixuan.dm.dto;

import java.math.BigDecimal;

/**
 * 测试使用的dto
 * @author Administrator
 *
 */
public class AdminGoodsInfo {
	private BigDecimal goods_number;
	private String goods_name;
	private Integer goods_id;
	private String user_name;
	private Integer barcode_id;    //使用包装类 可以使得取出数据为null时可以被赋值,int类型不能被赋值为null
	private String type_name;
    private String barcode_validity;  //这里的数据类型要和数据库中该字段的类型保持一致，例如表示时间的字段如果用Data类型，那么数据库中该字段就要使用Data类型
	public BigDecimal getGoods_number() {
		return goods_number;
	}
	public void setGoods_number(BigDecimal goods_number) {
		this.goods_number = goods_number;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Integer getBarcode_id() {
		return barcode_id;
	}
	public void setBarcode_id(Integer barcode_id) {
		this.barcode_id = barcode_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getBarcode_validity() {
		return barcode_validity;
	}
	public void setBarcode_validity(String barcode_validity) {
		this.barcode_validity = barcode_validity;
	}
	@Override
	public String toString() {
		return "AdminGoodsInfo [goods_number=" + goods_number + ", goods_name="
				+ goods_name + ", goods_id=" + goods_id + ", user_name="
				+ user_name + ", barcode_id=" + barcode_id + ", type_name="
				+ type_name + ", barcode_validity=" + barcode_validity + "]";
	}
    
}
