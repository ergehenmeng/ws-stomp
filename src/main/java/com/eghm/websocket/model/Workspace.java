package com.eghm.websocket.model;

import java.io.Serializable;
import java.util.Date;



/**
 * @time 下午2:41:042016年4月25日
 * @author fanyin
 * @table zf_workspace
 */
public class Workspace implements Serializable{
	
	private static final long serialVersionUID = -7545301027909797595L;

	private String id;
	
	private String spaceName;
	
	private String spaceNotice;//空间公告
	
	private Date createDate;
	
	private Integer userId;//所属用户
	
	private Integer state;//状态 0:正常 1:删除
	

	public String getSpaceNotice() {
		return spaceNotice;
	}

	public void setSpaceNotice(String spaceNotice) {
		this.spaceNotice = spaceNotice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
