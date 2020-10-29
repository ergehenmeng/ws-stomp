package com.eghm.websocket.action;/*package com.eghm.websocket.action;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eghm.websocket.model.Coordination;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.model.Slide;
import com.eghm.websocket.model.PartSlide;
import com.eghm.websocket.model.Text;
import com.eghm.websocket.model.UserChat;
import com.eghm.websocket.service.CoordinationService;
import com.eghm.websocket.service.DocumentService;
import com.eghm.websocket.service.SlideService;
import com.eghm.websocket.utils.DiffMatchPatch;
import com.eghm.websocket.utils.LimitQueue;

public class CoordinationAction {
	
	*//**
	 * 最大历史消息缓存量
	 *//*
	private Integer maxMessageCache;
	
	private SimpMessagingTemplate template;
	
	@Resource
	private DocumentService documentService;
	
	@Resource
	private SlideService slideService;
	
	@Resource
	private CoordinationService coordinationService;
	
	@Resource
	private FileService fileService;
	
	
	private Map<Integer, Object[]> coordinationCache = new HashMap<Integer, Object[]>();
	
	private DiffMatchPatch dmp = new DiffMatchPatch();//用来处理文件差异的合并等操作的工具
	
	@Autowired
    public CoordinationAction(SimpMessagingTemplate template) {
        this.template = template;
    }
	
	
	public Integer getMaxMessageCache() {
		return maxMessageCache;
	}


	public void setMaxMessageCache(Integer maxMessageCache) {
		this.maxMessageCache = maxMessageCache;
	}


	*//**
	 * 接收和转发的方法
	 * @param userChat
	 *//*
	@SuppressWarnings("unchecked")
	@MessageMapping("/user/Chat")
	public void userChat(UserChat userChat){
		 String dest = "/userChat/chat" + userChat.getCoordinationId();
		 this.template.convertAndSend(dest, userChat);
		 Object[] cache = coordinationCache.get(Integer.parseInt(userChat.getCoordinationId()));
		 try {
			userChat.setName(URLDecoder.decode(userChat.getName(), "UTF-8"));
			userChat.setChatContent(URLDecoder.decode(userChat.getChatContent(), "UTF-8"));
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 ((LimitQueue<UserChat>)cache[1]).offer(userChat);
	}
	
	@MessageMapping("/coordination/{coordinationId}")
	public void coordination(Text text ,@DestinationVariable("coordinationId") Integer coordinationId){
		System.out.println( "接收到text:" + text);
		String dest = "/coordination/coordination" + coordinationId;
		Object[] cache = coordinationCache.get(coordinationId);
		Document document = (Document) cache[0];
		LinkedList<DiffMatchPatch.Diff> diffs = dmp.diff_fromDelta(document.getContent(), text.getDelta());
	    LinkedList<DiffMatchPatch.Patch> patches = dmp.patch_make(document.getContent(), diffs);
	    Object[] o = dmp.patch_apply(patches, document.getContent());
	    document.setContent((String) o[0]);
	    cache[0] = document;
	    saveDocument(document);
	    this.template.convertAndSend(dest, text);
	}
	
	*//**
     * 接收的地址是/slide/{coordinationId}/{slidePartId}/{slidePartIndex},需要指定协同空间的id，页面id，和当前编辑的是第几个
     * 发送的地址是/coordination/slide+coordinationId，浏览器需要订阅这个地址才能接收到
     * @param delta 做的修改
     *//*
	@MessageMapping("/silde/{coordinationId}/{slidePartId}/slidePartIndex}")
	public void slideCoordination(Text text,@DestinationVariable("coordinationId") Integer coordinationId,
			@DestinationVariable("slidePartId")Integer slidePartId ,@DestinationVariable("slidePartIndex") Integer slidePartIndex ){
		System.out.println(text);
		String dest = "/coordination/silde" + coordinationId;
		Object[] cache = coordinationCache.get(coordinationId);
		Slide slide = (Slide) cache[0];
		PartSlide slidePart = slide.getPart().get(slidePartId);
		LinkedList<DiffMatchPatch.Diff> diffs = dmp.diff_fromDelta(slidePart.getContent(), text.getDelta());
        LinkedList<DiffMatchPatch.Patch> patches = dmp.patch_make(slidePart.getContent(), diffs);
        Object[] o = dmp.patch_apply(patches, slidePart.getContent());
       
        slidePart.setContent((String) o[0]);
        slide.getPart().set(slidePartIndex, slidePart);
        saveSlide(slidePart);
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("slidePartIndex", slidePartIndex);
        ret.put("slidePartId", slidePartId);
        ret.put("delta", text);
        this.template.convertAndSend(dest, ret);
	}
	
	
	@SubscribeMapping("/initDocument/{coordinationId}/{fileId}")
	public Map<String,Object> initDocument(@DestinationVariable("coordinationId") int coordinationId, @DestinationVariable("fileId") int fileId) {
        System.out.println("------------新用户进入文档，协同空间初始化---------");
        Map<String, Object> document = new HashMap<String, Object>();
        Coordination coordination = coordinationService.getCoordinationById(coordinationId);
        Document documentX = null;
        if (!coordinationCache.containsKey(coordination.getId())) {//如果从来都没有进入过协同空间
            //初始化协同空间的缓存
        	documentX = documentService.getDocumentByFileId(fileId);
            coordinationCache.put(coordination.getId(), new Object[]{documentX, new LimitQueue<UserChat>(maxMessageCache)});
        } else {//否则
            //取出缓存，并且放入到网页中初始化
        	documentX = (Document) coordinationCache.get(coordination.getId())[0];
        }
        document.put("document",documentX);
        document.put("chat",coordinationCache.get(documentX.getId())[1]);
        return document;
    }
	
	*//**
     * 初始化，初始化演示文稿和聊天记录
     *
     * @param coordinationId 协同空间的id
     *//*
    @SubscribeMapping("/initSlide/{coordinationId}/{fileId}")
    public Map<String,Object> initSlide(@DestinationVariable("coordinationId") int coordinationId, @DestinationVariable("fileId") int fileId) {
        System.out.println("------------新用户进入演示文稿，协同空间初始化---------");
        Map<String, Object> slide = new HashMap<String, Object>();
        Coordination coordination = coordinationService.getCoordinationById(coordinationId);
        Slide slideX;
        if (!coordinationCache.containsKey(coordination.getId())) {//如果从来都没有进入过协同空间
            //初始化协同空间的缓存
            slideX = slideService.getSlideByFileId(fileId);
            coordinationCache.put(coordination.getId(), new Object[]{slideX, new LimitQueue<UserChat>(maxMessageCache)});
        } else {//否则
            //取出缓存，并且放入到网页中初始化
            slideX = (Slide) coordinationCache.get(coordination.getId())[0];
        }
        slide.put("slide",slideX);
        slide.put("chat",coordinationCache.get(coordination.getId())[1]);
        return slide;
    }
	
	
    
    *//**
     * 创建一个文档
     * @param name 创建文档的名称
     * @param spaceId 空间的id
     * @return 创建文档的相关信息
     *//*
    @RequestMapping(value="/createDocuments/{spaceId}")
    @ResponseBody
    public Map<String,Object> createDocument(String name,@PathVariable int spaceId){
        Map<String,Object> ret = new HashMap<String, Object>();
        ret.put("id",0);
        return ret;
    }
    
    *//**
     * 创建一个演示文稿
     * @param name 创建演示文稿的名称
     * @param spaceId 空间的id
     * @return 创建演示文稿的相关信息
     *//*
    @RequestMapping(value="/createSlide/{spaceId}")
    @ResponseBody
    public Map<String,Object> createSlide(String name,@PathVariable int spaceId){
        int id = slideService.createSlide(name,spaceId);
        Map<String,Object> ret = new HashMap<String, Object>();
        ret.put("id",id);
        return ret;
    }
	
    
    *//**
     * 创建演示文稿的一个页面
     * @param slideId 演示文稿的id
     *//*
    @RequestMapping(value="/createSlidePart/{coordinationId}/{slideId}", method= RequestMethod.POST)
    public void createSlidePart(@PathVariable int coordinationId, @PathVariable int slideId){
        String dest = "/coordination/slidePart"+coordinationId;
        System.out.println(dest);
        int id = slideService.createPartSlide(slideId);
        Object[] cache = coordinationCache.get(coordinationId);
        Slide slide = (Slide) cache[0];
        PartSlide slidePart = new PartSlide();
        slidePart.setId(id);
        slidePart.setContent("");
        slidePart.setSlideId(slideId);
        slide.getPart().add(slidePart);
        Map<String,Object> ret = new HashMap<String, Object>();
        ret.put("id",id);
        this.template.convertAndSend(dest, ret);
    }
    
    
    *//**
     * 基本的演示文稿演示
     * @param fileId 演示的文件id
     * @param model 页面存放演示文稿
     * @return 跳转的页面
     *//*
    @RequestMapping(value = "/basicSlideShow/{fileId}")
    public String basicSlideShow(@PathVariable("fileId") int fileId, ModelMap model){
        Slide slide = slideService.getSlideByFileId(fileId);
        File file = fileService.getFileById(fileId);
        model.addAttribute("slide", slide);
        model.addAttribute("file", file);
        return "basicSlideShow";
    }
    
    @Async
	private void saveSlide(PartSlide slidePart) {
		slideService.saveSlidePart(slidePart);
	}


	@Async
	public void saveDocument(Document document){
		documentService.saveDocumentByFileId(document);
	}
	
	
	
	
	@SubscribeMapping("/createChat")
	public Map<String,Object> createChat(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("test", "厕所");
		return map;
	}
	
	
	
	
}
*/