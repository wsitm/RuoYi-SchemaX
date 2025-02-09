package com.ruoyi.rdbms.metainfo;

public interface IMetaInfoHandler {

    /**
     * 加载数据到缓存
     *
     * @param connectId 连接ID
     */
    void loadDataToCache(Long connectId);

}
