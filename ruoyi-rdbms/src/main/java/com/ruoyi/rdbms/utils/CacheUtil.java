package com.ruoyi.rdbms.utils;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.rdbms.constant.RdbmsConstants;
import com.ruoyi.rdbms.entity.vo.TableVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 缓存工具类
 *
 * @author wsitm
 */
public abstract class CacheUtil {

    /**
     * 获取缓存 Loading key
     *
     * @param connectId 连接ID
     * @return key
     */
    public static String getLoadingKey(Long connectId) {
        return String.format(RdbmsConstants.CACHE_LOADING_KEY, connectId);
    }

    /**
     * 获取缓存 History key
     *
     * @param connectId 连接ID
     * @return key
     */
    public static String getHistoryKey(Long connectId) {
        return String.format(RdbmsConstants.CACHE_HISTORY_KEY, connectId);
    }

    /**
     * 获取缓存 Metainfo key
     *
     * @param connectId 连接ID
     * @param nanoId    历史标记，nanoId
     * @return key
     */
    public static String getMetainfoKey(Long connectId, String nanoId) {
        return String.format(RdbmsConstants.CACHE_METAINFO_KEY, connectId, nanoId);
    }

    /**
     * 是否正在加载数据到缓存中
     *
     * @param connectId 连接ID
     * @return 布尔
     */
    public static Boolean isLoading(Long connectId) {
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String loadingKey = getLoadingKey(connectId);
        Boolean loading = redisCache.getCacheObject(loadingKey);
        return Boolean.TRUE.equals(loading);
    }

    /**
     * 加载数据到缓存情况，1、已加载，2、加载中，3、无缓存
     *
     * @param connectId 连接ID
     * @return 类型
     */
    public static Integer cacheType(Long connectId) {
        Boolean isLoading = isLoading(connectId);
        if (Boolean.TRUE.equals(isLoading)) {
            return 2;
        }
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);

        String historyKey = getHistoryKey(connectId);
        List<String> keyList = redisCache.getCacheList(historyKey);
        if (CollUtil.isEmpty(keyList)) {
            return 3;
        }
        String nanoId = keyList.get(0);
        if (Boolean.TRUE.equals(isLoading) && keyList.size() > 1) {
            // 如果正在加载中，临时先使用旧数据
            nanoId = keyList.get(1);
        }

        String realKey = getMetainfoKey(connectId, nanoId);
        Long size = redisCache.getCacheListSize(realKey);
        if (size == null || size <= 0L) {
            return 3;
        }

        return 1;
    }

    /**
     * 获取表元数据列表
     *
     * @param connectId 连接ID
     * @return 表元数据列表
     */
    public static List<TableVO> getTableMetaList(Long connectId) {
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        String loadingKey = getLoadingKey(connectId);
        String historyKey = getHistoryKey(connectId);

        Boolean isLoading = redisCache.getCacheObject(loadingKey);
        List<String> keyList = redisCache.getCacheList(historyKey);
        if (CollUtil.isEmpty(keyList)) {
            return new ArrayList<>();
        }
        String nanoId = keyList.get(0);
        if (Boolean.TRUE.equals(isLoading) && keyList.size() > 1) {
            // 如果正在加载中，临时先使用旧数据
            nanoId = keyList.get(1);
        }

        String realKey = getMetainfoKey(connectId, nanoId);
        List<TableVO> cacheList = redisCache.getCacheList(realKey);
        if (CollUtil.isEmpty(cacheList)) {
            return new ArrayList<>();
        }
        return cacheList;
    }


    /**
     * 清空缓存
     */
    public static void clearCache() {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class)
                .keys(RdbmsConstants.CACHE_KEY + "*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

}
