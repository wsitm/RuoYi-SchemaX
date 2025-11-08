package com.ruoyi.schemax.utils;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.schemax.constant.RdbmsConstants;
import com.ruoyi.schemax.entity.vo.TableVO;

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
     * 获取缓存 Metainfo key
     *
     * @param connectId 连接ID
     * @param subKey    子标记
     * @return key
     */
    public static String getMetainfoKey(Long connectId, String subKey) {
        return String.format(RdbmsConstants.CACHE_METAINFO_KEY, connectId, subKey);
    }

    /**
     * 加载数据到缓存情况，1、已加载，2、加载中，3、无缓存
     *
     * @param connectId 连接ID
     * @return 类型
     */
    public static Integer cacheType(Long connectId) {
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);

        String loadingKey = getLoadingKey(connectId);
        Boolean isLoading = redisCache.getCacheObject(loadingKey);
        if (Boolean.TRUE.equals(isLoading)) {
            return 2;
        }

        String mainKey = getMetainfoKey(connectId, RdbmsConstants.CACHE_METAINFO_SUB_KEY_MAIN);
        Long size = redisCache.getCacheListSize(mainKey);
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
        String mainKey = getMetainfoKey(connectId, RdbmsConstants.CACHE_METAINFO_SUB_KEY_MAIN);
        List<TableVO> cacheList = redisCache.getCacheList(mainKey);
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
