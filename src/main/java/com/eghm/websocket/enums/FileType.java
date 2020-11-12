package com.eghm.websocket.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

/**
 * @author 殿小二
 * @date 2020/11/3
 */
public enum FileType {

    /**
     * 演示文稿文件
     */
    PPT("PPT", "演示文稿文件"),

    /**
     * 普通文档
     */
    DOC("DOC", "普通文档"),

    /**
     * Markdown文档
     */
    MD("MD", "Markdown文档"),

    ;
    private String type;

    private String text;

    FileType(String type, String text) {
        this.type = type;
        this.text = text;
    }

    @JsonCreator
    public static FileType getInstance(String type) {
        return Arrays.stream(FileType.values()).filter(fileType -> fileType.getType().equals(type)).findFirst().orElse(PPT);
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
