package com.payment.web.beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="customeruser")
public class CustomerUser {
	@Id
	private int userid;
	private String username;
	@OneToOne
	@JoinColumn(name="customerid")
	private Customer customer;
	private String userpassword;
	public CustomerUser() {
		System.out.println("CustomerUser constructor");
	}
	public CustomerUser(int userid, String username, Customer customerid, String userpassword) {
		super();
		this.userid = userid;
		this.username = username;
		this.customer = customerid;
		this.userpassword = userpassword;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Customer getCustomerid() {
		return customer;
	}
	public void setCustomerid(Customer customerid) {
		this.customer = customerid;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	@Override
	public String toString() {
		return "CustomerUser [userid=" + userid + ", username=" + username + ", customerid=" + customer
				+ ", userpassword=" + userpassword + "]";
	}
	
}
