package com.eghm.websocket.utils;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 5为预留位
 * 5 + 41 + 8 + 10
 *
 * @author 二哥很猛
 * @date 2018/9/19 11:04
 */
@Slf4j
@Component
public class KeyGenerator {

    /**
     * 序列生成开始时间
     */
    private static final long EPOCH;

    /**
     * 自增序列占位长度 支持:1024/ms
     */
    private static final long SEQUENCE_BITS = 10L;

    /**
     * 机器id占位长度 256
     */
    private static final long WORKER_ID_BITS = 8L;

    /**
     * 自增序列最大值 1024,如果同一毫秒生成序列超过1024个,会等待下一毫秒再生成
     */
    private static final long SEQUENCE_MASK = (1L << SEQUENCE_BITS) - 1;

    /**
     * 机器id左移长度
     */
    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    /**
     * 时间戳左移长度 = 机器id占位 + 自增序列占位
     */
    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_BITS + SEQUENCE_BITS;

    /**
     * 上一次生成id时的序列值
     */
    private long sequence;

    /**
     * 上次生成id的时间
     */
    private long lastTime;

    static {
        //初始化时间 默认值
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.APRIL, 25);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }

    public synchronized Number generateKey() {
        return this.generateKey(WorkGroup.IP);
    }

    public synchronized Number generateKey(WorkGroup space) {

        long currentMillis = System.currentTimeMillis();

        Preconditions.checkState(lastTime <= currentMillis, "时钟回拨, 最后生成id时间: %d ms, 当前时间: %d ms", lastTime, currentMillis);

        if (lastTime == currentMillis) {
            ++sequence;
            sequence = sequence & SEQUENCE_MASK;
            if (0L == sequence) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            sequence = 0;
        }
        lastTime = currentMillis;

        if (log.isDebugEnabled()) {
            log.debug("分布式id生成信息:[{}]-[{}]-[{}]", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(lastTime)), space.getId(), sequence);
        }

        return ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (space.getId() << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    /**
     * 单一毫秒生成序列超过最大值,等待下一毫秒再生成
     *
     * @param lastTime 源时间
     * @return 新时间
     */
    private long waitUntilNextTime(final long lastTime) {
        long time = System.currentTimeMillis();
        while (time <= lastTime) {
            time = System.currentTimeMillis();
        }
        return time;
    }

}
