package indi.kaixuan.dm.dto;

public class Student {
	private Integer id;
	private String s_name;
	private String s_addr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getS_addr() {
		return s_addr;
	}

	public void setS_addr(String s_addr) {
		this.s_addr = s_addr;
	}

	@Override
	public String toString() {
		return "student [id=" + id + ", s_name=" + s_name + ", s_addr="
				+ s_addr + "]";
	}
}