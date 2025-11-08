package com.ruoyi.schemax.metainfo;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.schemax.constant.RdbmsConstants;
import com.ruoyi.schemax.entity.vo.ConnectInfoVO;
import com.ruoyi.schemax.entity.vo.TableVO;
import com.ruoyi.schemax.utils.CacheUtil;
import com.ruoyi.schemax.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbsMetaInfoHandler implements IMetaInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(AbsMetaInfoHandler.class);

    /**
     * 加载数据到缓存
     *
     * @param connectInfoVO 连接信息对象
     */
    public void loadDataToCache(ConnectInfoVO connectInfoVO) {
        // 获取连接ID
        Long connectId = connectInfoVO.getConnectId();
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);

        // 标记正在加载数据到缓存中
        String loadingKey = CacheUtil.getLoadingKey(connectId);
        redisCache.setCacheObject(loadingKey, true);

        try {
            // 开始加载表信息数据到临时缓存
            log.info("开始加载表信息数据到临时缓存……");
            // 获取临时缓存的键
            String tempKey = CacheUtil.getMetainfoKey(connectId, RdbmsConstants.CACHE_METAINFO_SUB_KEY_TEMP);
            // 清空临时缓存
            redisCache.deleteObject(tempKey);
            // 在临时缓存中存储一个空的表信息列表
//            redisCache.setCacheList(tempKey, new ArrayList<TableVO>());

            // 处理忽略的表名
            String[] skipStrArr = StrUtil.isEmpty(connectInfoVO.getWildcard()) ?
                    new String[]{"*"} : CommonUtil.dealStipStrArr(connectInfoVO.getWildcard().split(","));

            // 刷新数据到缓存，根据表名模式过滤并添加到临时缓存中
            flushData(connectId,
                    (tableName) -> CommonUtil.matchAnyIgnoreCase(tableName, skipStrArr),
                    (item) -> {
                        redisCache.setCacheObject(loadingKey, true);
                        redisCache.rightPushToCacheList(tempKey, item);
                    });
            // 完成加载表信息数据到临时缓存
            log.info("加载表信息数据到临时缓存完成 ^v^ ");

            // 复制临时缓存表信息数据到正式缓存
            log.info("复制临时缓存表信息数据到正式缓存");
            // 获取临时缓存表信息数据
            List<TableVO> cacheList = redisCache.getCacheList(tempKey);
            // 获取正式缓存的键
            String mainKey = CacheUtil.getMetainfoKey(connectId, RdbmsConstants.CACHE_METAINFO_SUB_KEY_MAIN);
            // 将表信息列表存储到正式缓存中
            redisCache.deleteObject(mainKey);
            redisCache.setCacheList(mainKey, cacheList);
        } catch (Exception e) {
            log.error("加载表信息数据到缓存失败", e);
        } finally {
            redisCache.setCacheObject(loadingKey, false);
        }
    }


    /**
     * 刷新数据
     *
     * @param connectId     连接ID
     * @param checkNameFunc 校验名称函数
     * @param consumer      消费者
     */
    public abstract void flushData(Long connectId, Function<String, Boolean> checkNameFunc, Consumer<TableVO> consumer);

}
