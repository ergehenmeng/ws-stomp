package com.eghm.websocket.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.crypto.digest.MD5;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.model.User;
import com.eghm.websocket.model.Workspace;
import com.eghm.websocket.service.DocumentService;
import com.eghm.websocket.service.UserService;
import com.eghm.websocket.service.WorkspaceService;
import com.eghm.websocket.utils.Constants;
import com.eghm.websocket.utils.StringUtil;
import com.eghm.websocket.utils.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class IndexController {
	
	
	@Resource
	private UserService userService;
	
	@Resource
	private WorkspaceService spaceService;
	
	@Resource
	private DocumentService documentService;
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	/**
	 * 登陆成功,进入聊天主界面
	 * 展示左侧工作空间以及工作空间的文档+工作空间的好友
	 * @param request
	 * @param userName
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request, String userName, String password ,Document document){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loginName", userName);
		User loginUser = userService.getUserByMap(map);
		if(loginUser != null && loginUser.getPwd().equals(MD5.create().digestHex(password))){
			request.getSession().setAttribute(Constants.SESSION_USER, loginUser);
			map.put("result", true);
			map.put("msg" , "用户信息验证通过");
		}else{
			map.put("result", false);
			map.put("msg" , "用户名或密码错误");
		}
		return map;
	}
	
	
	/**
	 * 登陆成功进入主界面
	 * @param request
	 * @param model 
	 * @param document 接收参数
	 * @return
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request,ModelMap model,Document document){
		User user = WebUtils.getUser(request);
		List<Workspace> list =  spaceService.getWorkspaceByUserId(user.getId());
		if(list != null && list.size() > 0){
			
			model.addAttribute("workspace",list.get(0));
			List<User> friendList = userService.getUserFriendList(user.getId(), list.get(0).getId());
			model.addAttribute("friendList",friendList);
			
			setOrderByValue(request,document,list.get(0).getId());
			document.setUserId(user.getId());
			List<Document> documentList = documentService.getDocumentByWorkspaceId(document);
			model.addAttribute("documentList", documentList);
		}
		model.addAttribute("workspaceList" ,list);
		return "home";
	}
	
	/**
	 * 设置orderBy的相关属性
	 * 设置查询条件
	 * @param request
	 * @param document
	 */
	private void setOrderByValue(HttpServletRequest request,Document document,String workspaceId){
		
		document.setWorkspaceId(workspaceId);
		document.setState(0);
		
		String orderBy = WebUtils.getAttribute(request, Constants.ORDER_BY);
		if(orderBy != null && orderBy.split(",").length == 2){
			String[] rows = orderBy.split(",");
			document.setRow(StringUtil.getUnderlineCase(rows[0]));
			document.setOrderType(rows[1]);
		}
	}
	
	
}
