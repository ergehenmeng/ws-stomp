package com.eghm.websocket.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
@Builder
public class User implements Serializable {

    public static final byte LOCK = 0;

    /**
     * 主键<br>
     * 表 : user<br>
     * 对应字段 : id<br>
     */
    private Long id;

    /**
     * 手机号码<br>
     * 表 : user<br>
     * 对应字段 : mobile<br>
     */
    private String mobile;

    /**
     * 昵称<br>
     * 表 : user<br>
     * 对应字段 : nick_name<br>
     */
    private String nickName;

    /**
     * 密码<br>
     * 表 : user<br>
     * 对应字段 : pwd<br>
     */
    private String pwd;

    /**
     * 初始化密码<br>
     * 表 : user<br>
     * 对应字段 : init_pwd<br>
     */
    private String initPwd;

    /**
     * 状态 1:正常 0:锁定<br>
     * 表 : user<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    /**
     * 新增时间<br>
     * 表 : user<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : user<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}