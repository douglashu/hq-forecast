package com.hq.scrati.framework;

import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/**
 * Created by Zale on 2016/12/7.
 */
@Component
public class IDGenerator {

    private static String MEMBER_SEQ = "memberseq";
    private static String MERCHANT_SEQ = "merchantseq";
    private static String TRANSFLOW_SEQ = "transflowseq";
    private static String ORDER_SEQ = "orderseq";

    private static Long cacheSize = 10L;

    private Map<String, Queue<Long>> seqMap = new HashMap<>();
    private Map<String, Semaphore> seqSemaphoreMap = new HashMap<>();
    private Map<String, Semaphore> getSeqSemaphoreMap = new HashMap<>();

    @Autowired
    @Qualifier("redisCache")
    private RedisCacheDao redisCacheDao;

    /**
     * 会员号规则 商户号+yyMMdd+5序列号+1校验位
     *
     *
     * @Author Zale
     * @Date 2016/12/7 下午5:23
     */
    public String generateMemberNo(long merId) throws InterruptedException {

        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.leftPad(String.valueOf(merId), 8, '0'))
                .append(DateUtils.formatDate(new SimpleDateFormat("yyMMdd"), new Date()));
        sb.append(StringUtils.leftPad(String.valueOf(getSeq(MEMBER_SEQ + merId,5)), 5, '0'));
        String nums = sb.toString();
        int check = AlgorithmUtil.Luhn.getCheckNo(nums);
        return nums + check;
    }

    /**
     * @param cacheKey 序列key
     * @param limit 序列长度
     * @Author Zale
     * @Date 2016/12/8 下午5:02
     *
     */
    private Long getSeq(String cacheKey, int limit) throws InterruptedException {
        Long seq;
        Queue<Long> memberSeqList = getOrinitQueue(cacheKey);
        Semaphore memberSemaphore = getOrinitSeqSemaphore(cacheKey);
        Semaphore getMemberSemaphore = getOrinitGetSeqSemaphore(cacheKey);
        if (!memberSeqList.isEmpty()&&memberSemaphore.tryAcquire()) {
            seq = memberSeqList.poll();
        } else {
            long maxVal = new Double(Math.pow(10,limit)).longValue()-10;
            if (getMemberSemaphore.tryAcquire()) {
                long nums = redisCacheDao.incrBy(cacheKey, cacheSize+1);
                if (nums >= maxVal) {
                    redisCacheDao.delete(cacheKey, Long.class);
                }
                int numSize = cacheSize.intValue();
                seq = nums -cacheSize;
                for (int i = 0; i <numSize; i++) {
                    memberSeqList.offer(nums - cacheSize + i+1);
                    memberSemaphore.release();
                }
                getMemberSemaphore.release();
            }else {
                memberSemaphore.acquire();
                seq = memberSeqList.poll();
            }
        }
        return seq;
    }

    private Semaphore getOrinitGetSeqSemaphore(String cacheKey) {
        Semaphore s = getSeqSemaphoreMap.get(cacheKey);
        if (s == null) {
            s = new Semaphore(1);
            getSeqSemaphoreMap.put(cacheKey, s);
        }
        return s;
    }
    private Semaphore getOrinitSeqSemaphore(String cacheKey) {
        Semaphore s = seqSemaphoreMap.get(cacheKey);
        if (s == null) {
            s = new Semaphore(0);
            seqSemaphoreMap.put(cacheKey, s);
        }
        return s;
    }

    private Queue<Long> getOrinitQueue(String cacheKey) {
        Queue<Long> memberSeqList = seqMap.get(cacheKey);
        if (memberSeqList == null) {
            memberSeqList = new ConcurrentLinkedQueue<>();
            seqMap.put(cacheKey, memberSeqList);
        }
        return memberSeqList;
    }

    /**
     * 商户号规则 4位银联地区码+yyMM+4序列号+1校验位
     *
     * @Author Zale
     * @Date 2016/12/7 下午5:23
     */
    public String generateMerchantNo(String unionCode) throws InterruptedException {

        StringBuilder sb = new StringBuilder();
        sb.append(unionCode).append(DateUtils.formatDate(new SimpleDateFormat("yyMM"), new Date()));
        sb.append(StringUtils.leftPad(String.valueOf(getSeq(MERCHANT_SEQ+unionCode,4)), 4, '0'));
        String nums = sb.toString();
        int check = AlgorithmUtil.Luhn.getCheckNo(nums);
        return nums + check;
    }

    /**
     * 生成流水号 商户ID + yyMMDDHHmm + 5位序列
     * @Author Zale
     * @Date 2016/12/8 下午4:57
     */
    public String generateFlowId(String merId) throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        sb.append(merId).append(DateUtils.formatDate(new SimpleDateFormat("yyMMddHHmm"), new Date()));
        sb.append(StringUtils.leftPad(String.valueOf(getSeq(TRANSFLOW_SEQ+merId,5)), 5, '0'));
        return sb.toString();
    }

    /**
     * @return
     * @throws InterruptedException
     */
    public String generateOrderId(String merId) throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        sb.append(merId).append(DateUtils.formatDate(new SimpleDateFormat("yyMMddHHmm"), new Date()));
        sb.append(StringUtils.leftPad(String.valueOf(getSeq(ORDER_SEQ + merId, 5)), 5, '0'));
        return sb.toString();
    }

    private  long __previous_time__ = 0L;
    private  int __machine_key__ = 0;
    private  int __sequence_no__ = 0;

    /*
     * 生成唯一向前滚动的序列号。 根据IP在不同机器上保持唯一性。 最大性能为10ms产生最多10个不重复序列号
     */
    public synchronized long generateUUID() {
        if (__machine_key__ == 0L) {
            // 获取本机IP地址
            int[] args = Convert.ipv4ToIntArray(MachineUtil.getFristIPv4());
            if (args == null)
                args = new int[] { (int) (Math.random() * 255),
                        (int) (Math.random() * 255),
                        (int) (Math.random() * 255),
                        (int) (Math.random() * 255) };
            __machine_key__ = args[0] + args[1] + args[2] + args[3];
            if (__machine_key__ > 999)
                __machine_key__ -= 1000;
            __machine_key__ = (__machine_key__ * 1000)
                    + ((int) (Math.random() * 9) * 100)
                    + ((int) (Math.random() * 9) * 10);
        }

        long now = System.currentTimeMillis();
        if (now == __previous_time__) {
            __sequence_no__++;
        } else {
            __previous_time__ = now;
            __sequence_no__ = 0;
        }
        long uuid =  __previous_time__ * 1000 + __machine_key__ + __sequence_no__;
        return uuid;
    }
}
