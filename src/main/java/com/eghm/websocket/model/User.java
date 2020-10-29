package com.eghm.websocket.model;

import java.io.Serializable;
import java.util.Date;



/**
 * @table sys_user
 * @author fanyin
 *
 */
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8339294556634154813L;

	private Integer id;
	
	private String loginName;
	
	private String nickName;
	
	private String pwd;
	
	private String initPwd;
	
	private String state;
	
	private String mobilePhone;
	
	private String email;
	
	private Date addTime;
	
	private Double money;
	
	
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getInitPwd() {
		return initPwd;
	}

	public void setInitPwd(String initPwd) {
		this.initPwd = initPwd;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginName=" + loginName + ", pwd=" + pwd
				+ ", initPwd=" + initPwd + ", state=" + state
				+ ", mobilePhone=" + mobilePhone + ", email=" + email
				+ ", addTime=" + addTime + ", money=" + money + "]";
	}
	
	
	
}
