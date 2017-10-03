package com.aitek.display;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee {

	private int eid;
	private String ename;
	private long sal;
	private String address;

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public long getSal() {
		return sal;
	}

	public void setSal(long sal) {
		this.sal = sal;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Employee(ResultSet rs) throws SQLException {
		this.eid = rs.getInt("eid");
		this.ename = rs.getString("ename");
		this.sal = rs.getLong("salary");
		this.address = rs.getString("address");
	}

	public Employee() {
	}

}
