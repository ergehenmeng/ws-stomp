package com.eghm.websocket.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 * @time 下午2:41:042016年4月25日
 * @author fanyin
 * @table zf_workspace
 */
@Data
public class Workspace implements Serializable{
	
	private static final long serialVersionUID = -7545301027909797595L;

	private String id;
	
	private String spaceName;
	
	private String spaceNotice;//空间公告
	
	private Date createDate;
	
	private Integer userId;//所属用户
	
	private Integer state;//状态 0:正常 1:删除
	
}
