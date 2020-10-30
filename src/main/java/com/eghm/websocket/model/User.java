package com.eghm.websocket.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * @table sys_user
 * @author fanyin
 *
 */
@Data
public class User implements Serializable{
	

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

}
