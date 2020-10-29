package com.eghm.websocket.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.eghm.websocket.exception.SystemException;
import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @table zf_word_ppt
 * @author fanyin
 *
 */
public class Document implements Serializable{
	
	private static final long serialVersionUID = -965945360908085971L;
	
	private static final List<String> DOC_TYPE = new ArrayList<String>();
	
	private static final String[] TYPE = {"word","ppt"};
	
	
	static{
		Collections.addAll(DOC_TYPE, TYPE);
	}
	
	private Integer id;
	
	private String workspaceId;//工作空间
	
	private String docName;//文档名称
	
	private Date createDate;//创建时间
	
	private String type;//ppt word两种文档类型
	
	private Integer state;//0:正常 1删除 
	
	private Date updateDate;//更新时间
	
	private Integer isShow;//是否显示 0:显示 1:隐藏
	
	private Integer userId;//用户ID
	
	private String docPassword;//该文档的访问密码
	
	
	private String order;//排序方式
	
	private String row;//排序列

	private String orderType;//排序类型
	
	private List<DocumentFile> documentFiles;
	
	
	public Document(){
		
	}
	public Document(Integer id){
		this.id = id;
	}
	
	public List<DocumentFile> getDocumentFiles() {
		return documentFiles;
	}


	public void setDocumentFiles(List<DocumentFile> documentFiles) {
		this.documentFiles = documentFiles;
	}


	public String getRow() {
		return row;
	}


	public void setRow(String row) {
		this.row = row;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	public String getOrder() {
		return order;
	}


	public void setOrder(String order) {
		this.order = order;
	}


	public String getDocPassword() {
		return docPassword;
	}


	public void setDocPassword(String docPassword) {
		this.docPassword = docPassword;
	}


	/**
	 * 校验数据是否合法
	 */
	public void verify(){
		if(docName.length() > 8){
			throw new SystemException("文档名称过长",1);
		}else if(!DOC_TYPE.contains(type)){
			throw new SystemException("非法文档类型",1);
		}else if(workspaceId == null){
			throw new SystemException("非法工作空间",1);
		}
	}
	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	
}
