package com.ruoyi.rdbms.metainfo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.rdbms.entity.vo.TableVO;
import com.ruoyi.rdbms.utils.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public abstract class AbsMetaInfoHandler implements IMetaInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(AbsMetaInfoHandler.class);

    /**
     * 加载数据到缓存
     *
     * @param connectId 连接ID
     */
    public void loadDataToCache(Long connectId) {
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);

        String loadingKey = CacheUtil.getLoadingKey(connectId);
        Boolean loading = CacheUtil.isLoading(connectId);
        if (Boolean.TRUE.equals(loading)) {
            log.warn("正在加载表信息到缓存中...");
            return;
        }
        redisCache.setCacheObject(loadingKey, true);

        String nanoId = IdUtil.nanoId();
        String historyKey = CacheUtil.getHistoryKey(connectId);
        redisCache.leftPushToCacheList(historyKey, nanoId);

        List<String> hisKeyList = redisCache.getCacheList(historyKey);
        if (CollUtil.isNotEmpty(hisKeyList) && hisKeyList.size() > 2) {
            // 只保留两份数据
            for (int i = hisKeyList.size() - 1; i >= 2; i--) {
                redisCache.deleteObject(CacheUtil.getMetainfoKey(connectId, hisKeyList.get(i)));
            }
            redisCache.trimList(historyKey, 0, 1);
        }

        String realKey = CacheUtil.getMetainfoKey(connectId, nanoId);
        try {
            flushData(connectId, (t) -> redisCache.rightPushToCacheList(realKey, t));
        } finally {
            redisCache.setCacheObject(loadingKey, false);
        }
    }


    /**
     * 刷新数据
     *
     * @param connectId 连接ID
     * @param consumer  消费者
     */
    public abstract void flushData(Long connectId, Consumer<TableVO> consumer);

}
