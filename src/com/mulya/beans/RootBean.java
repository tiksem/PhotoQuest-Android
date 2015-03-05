package com.mulya.beans;

/**
 * User: mulya
 * Date: 25/09/2014
 */
public class RootBean {
	/**
	 * Фамилия
	 */
	private com.mulya.beans.NameBean lastname;
	/**
	 * Имя
	 */
	private com.mulya.beans.NameBean firstname;
	/**
	 * Отчество
	 */
	private com.mulya.beans.NameBean middlename;

	public RootBean() {
	}

	public RootBean(com.mulya.beans.NameBean lastname, com.mulya.beans.NameBean firstname, com.mulya.beans.NameBean middlename) {
		this.lastname = lastname;
		this.firstname = firstname;
		this.middlename = middlename;
	}

	public com.mulya.beans.NameBean getLastname() {
		return lastname;
	}

	public void setLastname(com.mulya.beans.NameBean lastname) {
		this.lastname = lastname;
	}

	public com.mulya.beans.NameBean getFirstname() {
		return firstname;
	}

	public void setFirstname(com.mulya.beans.NameBean firstname) {
		this.firstname = firstname;
	}

	public com.mulya.beans.NameBean getMiddlename() {
		return middlename;
	}

	public void setMiddlename(NameBean middlename) {
		this.middlename = middlename;
	}
}
