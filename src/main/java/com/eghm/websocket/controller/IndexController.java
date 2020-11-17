package com.eghm.websocket.controller;

import cn.hutool.core.collection.CollUtil;
import com.eghm.websocket.dto.request.SearchDocumentRequest;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.model.Space;
import com.eghm.websocket.model.User;
import com.eghm.websocket.service.DocumentService;
import com.eghm.websocket.service.SpaceService;
import com.eghm.websocket.service.UserService;
import com.eghm.websocket.utils.ShiroUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 二哥很猛
 */
@Controller
public class IndexController {


    @Resource
    private UserService userService;

    @Resource
    private SpaceService spaceService;

    @Resource
    private DocumentService documentService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }


    /**
     * 登陆成功进入主界面
     */
    @RequestMapping("/home")
    public String home(Model model) {
        User user = ShiroUtil.getUser();
        // 用户所能访问工作空间
        List<Space> list = spaceService.getByUserId(user.getId());
        if (CollUtil.isNotEmpty(list)) {
            Long spaceId = list.get(0).getId();
            model.addAttribute("spaceId", spaceId);
            // 该工作空间的所有用户
            List<User> userList = userService.getUserList(user.getId(), spaceId);
            model.addAttribute("userList", userList);
            SearchDocumentRequest request = new SearchDocumentRequest();
            request.setSpaceId(spaceId);
            request.setUserId(user.getId());
            List<Document> documentList = documentService.getList(request);
            model.addAttribute("documentList", documentList);
        }
        model.addAttribute("spaceList", list);
        model.addAttribute("userId", user.getId());
        model.addAttribute("nickName", user.getNickName());
        return "home";
    }

}
