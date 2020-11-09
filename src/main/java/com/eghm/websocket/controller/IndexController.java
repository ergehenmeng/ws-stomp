package com.eghm.websocket.controller;

import cn.hutool.core.collection.CollUtil;
import com.eghm.websocket.model.Document;
import com.eghm.websocket.model.User;
import com.eghm.websocket.model.Workspace;
import com.eghm.websocket.service.DocumentService;
import com.eghm.websocket.service.UserService;
import com.eghm.websocket.service.WorkspaceService;
import com.eghm.websocket.utils.ShiroUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author 二哥很猛
 */
@Controller
public class IndexController {


    @Resource
    private UserService userService;

    @Resource
    private WorkspaceService spaceService;

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
    public String home(HttpServletRequest request, Model model) {
        User user = ShiroUtil.getUser();
        List<Workspace> list = spaceService.getByUserId(user.getId());
        if (CollUtil.isNotEmpty(list)) {
            Integer firstWorkspaceId = list.get(0).getId();
            model.addAttribute("workspaceId", firstWorkspaceId);
            List<User> friendList = userService.getUserFriendList(user.getId(), firstWorkspaceId);
            model.addAttribute("friendList", friendList);
            List<Document> documentList = documentService.getBySpaceId(firstWorkspaceId, null, null);
            model.addAttribute("documentList", documentList);
        }
        model.addAttribute("workspaceList", list);
        model.addAttribute("nickName", user.getNickName());
        return "home";
    }

}
