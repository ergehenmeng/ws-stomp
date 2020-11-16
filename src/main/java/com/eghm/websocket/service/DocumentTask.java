package com.eghm.websocket.service;

import com.eghm.websocket.dto.SendDoc;
import com.eghm.websocket.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 殿小二
 * @date 2020/11/16
 */
@Component
@Slf4j
public class DocumentTask {

    @Autowired
    private DocumentService documentService;

    private static class Holder {
        private static final ThreadPoolExecutor WORD_EXECUTOR = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), r -> new Thread(r, "同步WORD文档"), (r, executor) -> log.warn("同步WORD文档线程池已满"));
    }

    /**
     * 将文档信息同步到数据库
     * @param doc doc
     */
    public void syncContent(SendDoc doc) {
        Holder.WORD_EXECUTOR.execute(() -> documentService.updateContent(doc.getDocumentId(), doc.getContent()));
    }
}
