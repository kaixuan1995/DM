package indi.kaixuan.dm.dto;

public class Office {
	private int office_id;
	private String office_name;
	private int storehouse_id_fk;
	private String office_createtime;
	private int office_isdel;

	public int getOffice_id() {
		return office_id;
	}

	public void setOffice_id(int office_id) {
		this.office_id = office_id;
	}

	public String getOffice_name() {
		return office_name;
	}

	public void setOffice_name(String office_name) {
		this.office_name = office_name;
	}

	public int getStorehouse_id_fk() {
		return storehouse_id_fk;
	}

	public void setStorehouse_id_fk(int storehouse_id_fk) {
		this.storehouse_id_fk = storehouse_id_fk;
	}

	public String getOffice_createtime() {
		return office_createtime;
	}

	public void setOffice_createtime(String office_createtime) {
		this.office_createtime = office_createtime;
	}

	public int getOffice_isdel() {
		return office_isdel;
	}

	public void setOffice_isdel(int office_isdel) {
		this.office_isdel = office_isdel;
	}

	@Override
	public String toString() {
		return "Office [office_id=" + office_id + ", office_name="
				+ office_name + ", storehouse_id_fk=" + storehouse_id_fk
				+ ", office_createtime=" + office_createtime
				+ ", office_isdel=" + office_isdel + "]";
	}

}
