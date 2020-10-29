package com.eghm.websocket.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.MD5;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.model.DocumentFile;
import com.eghm.websocket.model.User;
import com.eghm.websocket.model.UserChat;
import com.eghm.websocket.service.DocumentFileService;
import com.eghm.websocket.service.DocumentService;
import com.eghm.websocket.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;


@Controller
@Slf4j
public class DocumentAction extends BaseAction {

	
	private static final String DEST_DOCUMENT_URL = "/document/";
	
	private Map<String,Object> model = new HashMap<String,Object>();
	
	/**
	 * 后台一步处理 客户端统一订阅一个接口即 /app/document/workspace/documentId 
	 * 其他均以type值判断
	 * type:1 实时更新文档内容
	 * type:2 实时更新聊天记录
	 * 
	 */
	

	@Resource
	private DocumentService documentService;
	
	@Resource
	private DocumentFileService documentFileService;
	
	//@Resource
	//private SimpMessagingTemplate messagingTemplate;
	
	@Resource
	private SimpMessageSendingOperations simpMessageSendingOperations;
	
	/**
	 * 聊天记录缓存
	 */
	private Map<Integer,LimitQueue<UserChat>> cacheChat = new HashMap<>();
	
	

	/**
	 * 创建文档
	 * @param request
	 * @param workspaceId 工作空间id
	 * @param document 创建文档的参数接收类
	 * @return
	 */
	@RequestMapping("/createDocument/{workspaceId}")
	@ResponseBody
	public Map<String,Object> createDocument(HttpServletRequest request,@PathVariable String workspaceId,Document document){
		
		document.setWorkspaceId(workspaceId);
		User user = WebUtils.getUser(request);
		document.setUserId(user.getId());
		document.verify();
		
		model.put("result", true);
		model.put("msg", documentService.createDocument(document));
		return model;
	}
	
	/**
	 * 查询文档信息
	 * @param request
	 * @param workspaceId 工作空间
	 * @param document 用于接收查询参数
	 * @return
	 */
	@RequestMapping("/getDocument/{workspaceId}")
	@ResponseBody
	public Map<String,Object> getDocument(HttpServletRequest request,@PathVariable String workspaceId,Document document){
		if(! StringUtils.isEmpty(document.getOrder())){
			String[] order = document.getOrder().split(",");
			if(order.length == 2){
				document.setRow(StringUtil.getUnderlineCase(order[0]));
				document.setOrderType(order[1]);
			}
			request.getSession().setAttribute(Constants.ORDER_BY,document.getOrder());
		}
		
		if(document.getIsShow() != null){
			request.getSession().setAttribute(Constants.HIDDEN, document.getIsShow());
		}
		
		User user = WebUtils.getUser(request);
		document.setState(0);
		document.setUserId(user.getId());
		document.setWorkspaceId(workspaceId);
		
		model.put("result", true);
		model.put("msg", documentService.getDocumentByWorkspaceId(document));
		return model;
	}
	
	/**
	 * 删除文档
	 * @param request
	 * @param workspaceId
	 * @param document
	 * @return
	 */
	@RequestMapping("/deleteDocument/{workspaceId}")
	@ResponseBody
	public Map<String,Object> deleteDocument(HttpServletRequest request,@PathVariable String workspaceId,Document document){
		User user = WebUtils.getUser(request);
		document.setUserId(user.getId());
		List<Document> list = documentService.getDocumentByWorkspaceId(document);
		if(list != null && list.size() > 0){
			documentService.deleteDocumentById(document);
			model.put("result", true);
			model.put("msg", "删除文档成功");
		}else{
			model.put("result", false);
			model.put("msg", "无操作该文件的权限");
		}
		return model;
	}
	
	/**
	 * 更新文档名称
	 * @param request
	 * @param workspaceId
	 * @param document
	 * @param docName
	 * @return
	 */
	@RequestMapping("/updateDocument/{workspaceId}")
	@ResponseBody
	public Map<String,Object> updateDocument(HttpServletRequest request ,@PathVariable String workspaceId,Document document,String docName){
		User user = WebUtils.getUser(request);
		document.setUserId(user.getId());
		document.setDocName(null);
		List<Document> list = documentService.getDocumentByWorkspaceId(document);
		if(list != null && list.size() > 0){
			document.setDocName(docName);
			documentService.updateDocument(document);
			model.put("result", true);
			model.put("msg", "文档重命名成功");
		}else{
			model.put("result", false);
			model.put("msg", "无操作该文件的权限");
		}
		return model;
	}
	/**
	 * 文档加密
	 * @param request
	 * @param workspaceId
	 * @param document
	 * @param docPassword
	 * @return
	 */
	@RequestMapping("/createPassword/{workspaceId}")
	@ResponseBody
	public Map<String,Object> createPassword(HttpServletRequest request ,@PathVariable String workspaceId,Document document,String docPassword){
		User user = WebUtils.getUser(request);
		document.setUserId(user.getId());
		List<Document> list = documentService.getDocumentByWorkspaceId(document);
		if(list != null && list.size() > 0){
			document.setDocPassword(MD5.create().digestHex(docPassword));
			documentService.updateDocument(document);
			model.put("result", true);
			model.put("msg", "文档加密成功");
		}else{
			model.put("result", false);
			model.put("msg", "无操作该文件的权限");
		}
		return model;
	}
	
	/**
	 * 密码验证
	 * @param request
	 * @param workspaceId
	 * @param document
	 * @return
	 */
	@RequestMapping("/checkPassword/{workspaceId}")
	@ResponseBody
	public Map<String,Object> checkPassword(HttpServletRequest request,@PathVariable String workspaceId,Document document){
		User user = WebUtils.getUser(request);
		document.setUserId(user.getId());
		Document doc = documentService.getDocumentById(document);
		if(doc != null){
			model.put("result", true);
			model.put("msg", "密码验证通过");
		}else{
			model.put("result", false);
			model.put("msg", "密码验证错误");
		}
		return model;
	}
	
	/**
	 * 文档管理页面
	 * @param workspaceId
	 * @param documentId
	 * @param model
	 * @return
	 */
	@RequestMapping("/document/{workspaceId}/{documentId}")
	public String document(@PathVariable String workspaceId,@PathVariable Integer documentId,ModelMap model){
		model.addAttribute("workspaceId", workspaceId);
		model.addAttribute("documentId", documentId);
		return "document";
	}
	
	
	/**
	 * 默认被调用一次
	 * 初始化某个文档
	 * @return
	 */
	@SubscribeMapping("/initDocument/{workspaceId}/{documentId}")
	public Map<String,Object> initDocument(SimpMessageHeaderAccessor accessor ,@DestinationVariable Integer workspaceId,@DestinationVariable Integer documentId){
		log.debug("文档空间: 工作空间ID: " + workspaceId + " 文档ID " + documentId);
		log.debug("当前类: " + this);
		Document document = new Document(documentId);
		document = documentService.getDocumentById(document);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("document", document);
		if(!cacheChat.containsKey(documentId)){//空间缓存不存在,则创建
			cacheChat.put(documentId, new LimitQueue<UserChat>(50));
		}
		result.put("chat", cacheChat.get(documentId));
		
		return result;
	}
	
	
	
	/**
	 * 接收并转发聊天室的消息
	 * @param accessor 获取用户sessionId等信息
	 * @param userChat 接收和要转发的信息
	 */
	@MessageMapping("/userChat")
	public void sendMessage(SimpMessageHeaderAccessor accessor , UserChat userChat){
		Map<String,Object> map = accessor.getSessionAttributes();
		User user = (User) map.get(Constants.SESSION_USER);
		if(user != null){
			userChat.setId(user.getId());
			try {
				userChat.setChatContent(HtmlUtils.htmlEscape(URLDecoder.decode(userChat.getChatContent(), "utf-8")));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
 			userChat.setNickName(user.getNickName());
			userChat.setCreateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			userChat.setType(1);
			simpMessageSendingOperations.convertAndSend(DEST_DOCUMENT_URL + userChat.getWorkspaceId() + "/" + userChat.getDocumentId(), userChat);
			LimitQueue<UserChat> limit = cacheChat.get(userChat.getDocumentId());
			limit.offer(userChat);
		}
	}
	
	/**
	 * 切换document列表
	 * @param id
	 * @return
	 */
	@RequestMapping("/changeFile")
	@ResponseBody
	public DocumentFile changeFile(Integer id){
		return documentFileService.getDocumentFileById(id);
	}
	
	/**
	 * 文档内容改变后同步
	 * @param accessor
	 */
	@MessageMapping("/documentChange")
	public void documentFile(SimpMessageHeaderAccessor accessor , DocumentFile documentFile){
		Map<String,Object> map = accessor.getSessionAttributes();
		User user = (User) map.get(Constants.SESSION_USER);
		Map<String,Object> result = new HashMap<String,Object>();
		if(user != null){
			try {
				documentFile.setContent(URLDecoder.decode(documentFile.getContent(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			result.put("id", documentFile.getId());
			result.put("userId", user.getId());
			result.put("content", documentFile.getContent());
			result.put("type", 2);
			simpMessageSendingOperations.convertAndSend(DEST_DOCUMENT_URL + documentFile.getWorkspaceId() + "/" + documentFile.getDocumentId(), result);
			documentFileService.updateDocumentFile(documentFile);
		}
	}
	
	
	
}
